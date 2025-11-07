package com.example.api_practice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ApiPracticeController {

    // /helloにアクセスするとHello Worldという文字列が返される関数
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    // /goodbyeにアクセスすると Good Byeという文字列が返される関数
    @GetMapping("/goodbye")
    public String goodbe() {
        return "GoodBye!";
    }

    // URLの一部をパス変数として受け取り "Hello, {name}!" と返す関数
    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

}