package com.acme.spring_cloud_service_hello_world;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;


@Service
class HelloWorldService {

   private final DiscoveryClient discoveryClient;
   private final RestClient restClient;

   HelloWorldService(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
      this.discoveryClient = discoveryClient;
      this.restClient = restClientBuilder.build();
   }

   String discoverHelloWorld() {
      return String.format(
          "%s %s",
          getResponseContent(getUri(HelloWorldServiceLookupEnum.HELLO)),
          getResponseContent(getUri(HelloWorldServiceLookupEnum.WORLD))
      );
   }

   private String getResponseContent(URI uri) {
      return restClient.get().uri(uri).retrieve().body(String.class);
   }

   private URI getUri(HelloWorldServiceLookupEnum helloWorldServiceLookupEnum) {
      return URI.create(
          discoveryClient.getInstances(helloWorldServiceLookupEnum.getApplicationName())
              .getFirst()
              .getUri() + helloWorldServiceLookupEnum.getRequestMapping()
      );
   }


}
