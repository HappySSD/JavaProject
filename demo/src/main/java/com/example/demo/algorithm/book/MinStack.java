package com.example.demo.algorithm.book;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @author ransahojie
 * @Description: 带有getMin功能的栈
 * @date 2018/8/7 14:28
 */
public class MinStack {
    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    public MinStack(Stack<Integer> stackData, Stack<Integer> stackMin) {
        this.stackData = stackData;
        this.stackMin = stackMin;
    }

    public void push(Integer data) {
        Integer top = stackMin.peek();
        if (top == null || top >= data) {
            stackMin.push(data);
        }

        stackData.push(data);
    }

    public Integer pop(){
        if(stackData.isEmpty()){
            throw new RuntimeException("无数据");
        }
        Integer peek = stackData.pop();
        Integer top =stackMin.peek();
        if(top == peek){
            stackMin.pop();
        }

        return peek;
    }

    public Integer getMin(){
        if(stackMin.isEmpty()){
            throw new RuntimeException("无数据");
        }

        return stackMin.pop();
    }

}
