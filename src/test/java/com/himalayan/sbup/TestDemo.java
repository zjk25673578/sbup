package com.himalayan.sbup;

import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.net.URL;

public class TestDemo {
    public static void main(String[] args) {
        /*StateEnum s = StateEnum.stateOf(-1);
        System.out.println(s.getMessage());*/

        try {
            HttpClient client = HttpClient.New(new URL("http://127.0.0.1/8080/test"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
