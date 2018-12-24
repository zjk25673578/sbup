package com.himalayan.sbup;

public class TestDemo {
    public static void main(String[] args) {
        StateEnum s = StateEnum.stateOf(-1);
        System.out.println(s.getMessage());
    }
}
