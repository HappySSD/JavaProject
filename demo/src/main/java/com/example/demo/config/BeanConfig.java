package com.example.demo.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/**
 * Created by Administrator on 2017/4/27.
 */
@Configuration
public class BeanConfig {

  public String httpClientTimeout = "3000";

  private static class ContentLengthHeaderRemover implements HttpRequestInterceptor {

    @Override
    public void process(HttpRequest request, HttpContext context)
        throws HttpException, IOException {
      request.removeHeaders(HTTP.CONTENT_LEN);// fighting org.apache.http.protocol.RequestContent's
      // ProtocolException("Content-Length header already present");
    }
  }

  @Bean
  RestTemplate restTemplate() throws Exception {

    HttpClientBuilder httpClientBuilder = HttpClients.custom();

    SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
      public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        return true;
      }
    }).build();
    httpClientBuilder.setSslcontext(sslContext);
    HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
    SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
        hostnameVerifier);
    Registry<ConnectionSocketFactory> socketFactoryRegistry =
        RegistryBuilder.<ConnectionSocketFactory>create()
        .register("http", PlainConnectionSocketFactory.getSocketFactory())
        .register("https", sslSocketFactory).build();
    // 长连接保持30秒
    PoolingHttpClientConnectionManager pollingConnectionManager =
        new PoolingHttpClientConnectionManager(
        socketFactoryRegistry, null, null, null, 30, TimeUnit.SECONDS);
    // 总连接数
    pollingConnectionManager.setMaxTotal(1000);
    // 同路由的并发数
    pollingConnectionManager.setDefaultMaxPerRoute(1000);

    httpClientBuilder.disableAutomaticRetries() //关闭自动处理重定向
        .setRedirectStrategy(new LaxRedirectStrategy());//利用LaxRedirectStrategy处理POST重定向问题
    httpClientBuilder.addInterceptorFirst(new ContentLengthHeaderRemover());

    httpClientBuilder.setConnectionManager(pollingConnectionManager);
    // 重试次数，默认是3次，没有开启
    httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
    // 保持长连接配置，需要在头添加Keep-Alive
    httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());

		/*List<Header> headers = new ArrayList<>();
		headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36
		(KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
		headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
		headers.add(new BasicHeader("Accept-Language", "zh-CN"));
		headers.add(new BasicHeader("Connection", "Keep-Alive"));
		httpClientBuilder.setDefaultHeaders(headers);*/

    HttpClient httpClient = httpClientBuilder.build();

    // httpClient连接配置，底层是配置RequestConfig
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
        new HttpComponentsClientHttpRequestFactory(
        httpClient);
    // 连接超时
    clientHttpRequestFactory.setConnectTimeout(Integer.parseInt(httpClientTimeout));
    // 数据读取超时时间，即SocketTimeout
    clientHttpRequestFactory.setReadTimeout(Integer.parseInt(httpClientTimeout));
    // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
    clientHttpRequestFactory.setConnectionRequestTimeout(200);
    // 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
    // clientHttpRequestFactory.setBufferRequestBody(false);

    return reInitMessageConverter(new RestTemplate(clientHttpRequestFactory));
  }

  /*
   *初始化RestTemplate，RestTemplate会默认添加HttpMessageConverter
   * 添加的StringHttpMessageConverter非UTF-8
   * 所以先要移除原有的StringHttpMessageConverter，
   * 再添加一个字符集为UTF-8的StringHttpMessageConvert
   * */
  private RestTemplate reInitMessageConverter(RestTemplate restTemplate) {
    List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
    HttpMessageConverter<?> converterTarget = null;
    for (HttpMessageConverter<?> item : converterList) {
      if (item.getClass() == StringHttpMessageConverter.class) {
        converterTarget = item;
        break;
      }
    }

    if (converterTarget != null) {
      converterList.remove(converterTarget);
    }
    HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
    converterList.add(converter);
    return restTemplate;
  }
}
