package com.acme.spring_cloud_service_hello_world;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello-world")
class HelloWorldController {

   private final HelloWorldService helloWorldService;

   HelloWorldController(HelloWorldService helloWorldService) {
      this.helloWorldService = helloWorldService;
   }

   @GetMapping("/get")
   String getHelloWorld() {
      return "Hello World!";
   }

   @GetMapping("/discover")
   String discoverHelloWorld() {
      return helloWorldService.discoverHelloWorld();
   }
}
