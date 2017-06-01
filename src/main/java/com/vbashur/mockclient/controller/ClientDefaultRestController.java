package com.vbashur.mockclient.controller;

import com.vbashur.mockclient.restauth.RestTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientDefaultRestController {

    @Autowired
    RestTemplateFactory restTemplateFactory;

    @RequestMapping("/")
    @ResponseBody
    public String getMainView() {
        return "Hi there!";
    }

    @RequestMapping("/auth")
    @ResponseBody
    public String auth() {
        RestTemplate restTemplate = restTemplateFactory.getObject();
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor("username", "password"));
        restTemplate.exchange(
                "http://localhost:8080/spring-security-rest-template/api/foos/1",
                HttpMethod.GET, null, Void.class); //TODO

        return "Request was sent";

    }
}
