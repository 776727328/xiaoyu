package com.example.chatgpt.controller;

import com.example.chatgpt.produce.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private Produce01 produce01;
    @Autowired
    private Produce02 produce02;

    @Autowired
    private Produce03 produce03;
    @Autowired
    private Produce04 produce04;

    @Autowired
    private Produce05 produce05;

    @GetMapping("/test01")
    public void test01(){
        produce01.testHello();
    }

    @GetMapping("/test02")
    public void test02(){
        produce02.testWork();
    }

    @GetMapping("/test03")
    public void test03(){
        produce03.testFanout();
    }

    @GetMapping("/test04")
    public void test04(){
        produce04.testRoute();
    }

    @GetMapping("/test05")
    public void test05(){
        produce05.testTopic();
    }
}
