package com.acme.spring_cloud_service_hello_world;

enum HelloWorldServiceLookupEnum {
   HELLO("SPRING-CLOUD-SERVICE-HELLO", "/hello/get"),
   WORLD("SPRING-CLOUD-SERVICE-WORLD", "/world/get");

   private final String applicationName;
   private final String requestMapping;

   HelloWorldServiceLookupEnum(
       String applicationName,
       String requestMapping
   ) {
      this.applicationName = applicationName;
      this.requestMapping = requestMapping;
   }

   public String getApplicationName() {
      return applicationName;
   }

   public String getRequestMapping() {
      return requestMapping;
   }
}
