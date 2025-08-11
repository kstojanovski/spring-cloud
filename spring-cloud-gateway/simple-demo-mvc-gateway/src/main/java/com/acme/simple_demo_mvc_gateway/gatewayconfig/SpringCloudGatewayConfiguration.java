package com.acme.simple_demo_mvc_gateway.gatewayconfig;

import static com.acme.simple_demo_mvc_gateway.gatewayconfig.filters.BeforeRequestPokerFunctions.combination;
import static com.acme.simple_demo_mvc_gateway.gatewayconfig.filters.BeforeRequestPokerFunctions.createAndPutHeader;
import static com.acme.simple_demo_mvc_gateway.gatewayconfig.filters.BeforeRequestPokerFunctions.extractAuthToken;
import static com.acme.simple_demo_mvc_gateway.gatewayconfig.filters.BeforeRequestPokerFunctions.extractCompany;
import static com.acme.simple_demo_mvc_gateway.gatewayconfig.filters.BeforeRequestPokerFunctions.extractUser;
import static com.acme.simple_demo_mvc_gateway.gatewayconfig.filters.BeforeRequestPokerFunctions.setPathDependOnParam;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.removeRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import com.acme.simple_demo_mvc_gateway.gatewayconfig.service.FeatureFlagService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class SpringCloudGatewayConfiguration {

  private final FeatureFlagService featureFlagService;

  SpringCloudGatewayConfiguration(FeatureFlagService featureFlagService) {
    this.featureFlagService = featureFlagService;
  }

  @Bean
  public RouterFunction<ServerResponse> headerTransformationRoute() {
    return RouterFunctions.route()
        .GET("/api/hello-routed-world/**", http())
        .before(uri("http://localhost:8088"))
        .before(setPath("/special"))
        .before(extractAuthToken()) // combination()
        .before(extractCompany()) // combination()
        .before(extractUser()) // combination()
        .before(createAndPutHeader()) // combination()
        .before(removeRequestHeader("x-company-id"))
        .before(removeRequestHeader("x-user-id"))
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> headerTransformationAndOptionalRedirectingRoute() {
    return RouterFunctions.route()
        .GET("/api/hello-very-routed-world/**", http())
        .before(uri("http://localhost:8088")) // default, always need to be here
        .before(combination())
//        .before(extractAuthToken().andThen(extractCompany().andThen(extractUser()).andThen(createAndPutHeader())))
        .before(removeRequestHeader("x-company-id"))
        .before(removeRequestHeader("x-user-id"))
//        .before(setPathDependOnFeatureFlag(featureFlagService.isFlag()))
        .before(
            setPath(
              featureFlagService.isFlag() ?
                  "http://localhost:8088" : "http://localhost:8088/special"
            )
        )
        .before(setPathDependOnParam())
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> composedRoutes(
      RouterFunction<ServerResponse> headerTransformationRoute,
      RouterFunction<ServerResponse> headerTransformationAndOptionalRedirectingRoute
  ) {
    return headerTransformationRoute
        .and(headerTransformationAndOptionalRedirectingRoute);
  }

}
