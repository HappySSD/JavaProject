package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);


    }

    /**
     * 第一个参数是py文件路径 后面是模型参数
     * @param args
     */
    public static void test(String[] args) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile(args[0]);

        PyFunction pyFunction = interpreter.get("XgboostModle", PyFunction.class); // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
        PyObject pyObject = pyFunction.__call__(new PyString(args[1]),new PyString(args[2]),new PyString(args[3])); // 调用函数

        System.out.println(pyObject);
    }
}
