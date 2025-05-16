package com.acme.spring_cloud_service_hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

   @GetMapping("/get")
   String getHello() {
      return "Hello";
   }
}
