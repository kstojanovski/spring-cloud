package com.acme.spring_cloud_service_world;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/world")
public class WorldController {

   @GetMapping("/get")
   String getWorld() {
      return "World";
   }
}
