package com.acme.simple_demo_mvc_gateway.gatewayconfig.filters;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;
import org.springframework.web.servlet.function.ServerRequest;

public abstract class BeforeRequestPokerFunctions {

  public static Function<ServerRequest, ServerRequest> extractAuthToken() {
    return request -> ServerRequest.from(request)
        .attribute(
            "authToken",
            new AuthToken(
                request.headers().firstHeader("Authorization")
            )
        )
        .build();
  }

  public static Function<ServerRequest, ServerRequest> extractCompany() {
    return request -> ServerRequest.from(request)
        .attribute(
            "company",
            new Company(
                request.headers().firstHeader("X-Company-Id"),
                request.headers().firstHeader("X-Company-Name")
            )
        )
        .build();
  }

  public static Function<ServerRequest, ServerRequest> extractUser() {
    return request -> ServerRequest.from(request)
        .attribute(
            "user",
            new User(
                request.headers().firstHeader("X-User-Id"),
                request.headers().firstHeader("X-User-Role")
            )
        )
        .build();
  }

  public static Function<ServerRequest, ServerRequest> combination() {
    return extractAuthToken().andThen(extractCompany().andThen(extractUser()).andThen(createAndPutHeader()));
  }

  public static Function<ServerRequest, ServerRequest> createAndPutHeader() {
    return (request) -> {
      AuthToken authToken = request.attribute("authToken").isPresent()
          ? (AuthToken) request.attribute("authToken").get()
          : null;
      Company company = request.attribute("company").isPresent()
          ? (Company) request.attribute("company").get()
          : null;
      User user = request.attribute("user").isPresent()
          ? (User) request.attribute("user").get()
          : null;

      return ServerRequest.from(request)
          .header(
              "my-authToken", authToken != null ? authToken.token() : "empty"
          )
          .header(
              "my-company-id", company != null ? company.id() : "empty"
          )
          .header(
              "my-company-name", company != null ? company.name() : "empty"
          )
          .header(
              "my-user-id", user != null ? user.id() : "empty"
          )
          .header(
              "my-user-role", user != null ? user.role() : "empty"
          )
          .build();
    };
  }

  public static Function<ServerRequest, ServerRequest> setPathDependOnFeatureFlag(boolean flag) {
    return request -> {
      try {
        return ServerRequest.from(request)
            .uri(
                flag ?
                    new URI("http://localhost:8088") :
                    new URI("http://localhost:8088/special")
            )
            .build();
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }
    };
  }

  public static Function<ServerRequest, ServerRequest> setPathDependOnParam() {
    return request -> {
      try {
        return ServerRequest.from(request)
            .uri(
                request.param("tournamentType").isPresent() ?
                    new URI("http://localhost:8088/legacy") :
                    new URI("http://localhost:8088/modern")
            )
            .build();
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }
    };
  }


}
