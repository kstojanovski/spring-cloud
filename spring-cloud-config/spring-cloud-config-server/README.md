# Spring Cloud configuration server
## Introduction
Intro
## Creating a Key Store for Testing
Link: https://docs.spring.io/spring-cloud-config/reference/server/creating-a-key-store-for-testing.html
## Keys creation
To create the key pair: \
`keytool -genkeypair -alias myalias -keyalg RSA -dname "CN=Spring Boot Config Server,OU=Unit,O=Organization,L=City,S=State,C=DE" -keypass mysecret -keystore spring-cloud-config-server.jks -storepass mypassword -storetype JKS`

To list the content of the key pair: \
`keytool -list -v -keystore spring-cloud-config-server.jks`
## Calling data from this application
To encrypt the passwords with the keys: \
`POST localhost:8888/encrypt` and add the password body part as raw content.

To list the content of the application.property file for the application "spring-cloud-client-a" and the profile "dev": \
`GET localhost:8888/spring-cloud-client-a/dev`

