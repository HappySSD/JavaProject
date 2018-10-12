package com.example.demo.algorithm.datastructure;

import java.util.Stack;

/**
 * @author ransahojie
 * @Description: min stack
 * @date 2018/9/30 16:40
 */
class MinStack {

    private Stack<Integer> value;
    private Stack<Integer> min;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        value = new Stack<>();
        min = new Stack<>();
    }

    public void push(int x) {
        value.push(x);
        if (min.isEmpty() || (!min.isEmpty() && (x < min.pop()))) min.push(x);
    }

    public void pop() {
        if (!value.isEmpty()) {
            int top = value.pop();
            if (top == min.peek()) min.pop();
        }
    }

    public int top() {
        return value.peek();
    }

    public int getMin() {
        return min.peek();
    }
}
