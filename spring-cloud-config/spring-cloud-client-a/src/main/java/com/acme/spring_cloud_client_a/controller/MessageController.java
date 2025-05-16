package com.acme.spring_cloud_client_a.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RefreshScope
public class MessageController {

   @Value("${my.variable}")
   private String variable;

   @GetMapping("/get")
   String getMessage() {
      return variable;
   }
}
