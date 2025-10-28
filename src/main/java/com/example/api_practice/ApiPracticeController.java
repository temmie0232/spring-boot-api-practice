package com.example.api_practice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ApiPracticeController {

    // /helloアにアクセスするとHello Worldという文字列が返される関数
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    // /goodbyeにあくせすすると Good Byeという文字列が返される関数
    @GetMapping("/goodbye")
    public String goodbe() {
        return "GoodBye!";
    }
}