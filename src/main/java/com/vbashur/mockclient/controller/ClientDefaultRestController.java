package com.vbashur.mockclient.controller;

import com.vbashur.mockclient.restauth.RestTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        return "Mock client for auth requests";
    }

    @RequestMapping(value="/auth", method=RequestMethod.GET)
    @ResponseBody
    public String auth(@RequestParam("target") String target,
                       @RequestParam("user") String user,
                       @RequestParam("password") String password) {
        RestTemplate restTemplate = restTemplateFactory.getObject();
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor(user, password));
        restTemplate.exchange(
                target,
                HttpMethod.GET, null, Void.class); //TODO

        return "Request was sent";

    }
}
