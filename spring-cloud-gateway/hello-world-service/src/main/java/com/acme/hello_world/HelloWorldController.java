package com.acme.hello_world;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloWorldController {

  Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

  @GetMapping
  public String helloWorld() {
    return "Hello World";
  }

  @GetMapping("/list-headers")
  public String helloWorld(@RequestHeader Map<String, String> headers) {
    printHeaders(headers);
    return "Hello World";
  }

  @GetMapping("/special")
  public String helloSpecialWorld(@RequestHeader Map<String, String> headers) {
    printHeaders(headers);
    return "Hello special World";
  }

  @GetMapping("/legacy")
  public String legacy(@RequestHeader Map<String, String> headers) {
    printHeaders(headers);
    return "Hello World - legacy!";
  }

  @GetMapping("/modern")
  public String modern(@RequestHeader Map<String, String> headers) {
    printHeaders(headers);
    return "Hello World - modern!";
  }

  private void printHeaders(Map<String, String> headers) {
    logger.info("*** Headers Begin ***");
    headers.forEach((key, value) -> logger.info("Header {} = {}", key, value));
    logger.info("*** Headers End ***");
  }
}
