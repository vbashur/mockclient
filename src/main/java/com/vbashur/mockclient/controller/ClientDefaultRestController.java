package com.vbashur.mockclient.controller;

import com.vbashur.mockclient.restauth.RestTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.*;

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
    public ResponseEntity<? extends Serializable> auth(@RequestParam("target") String target,
                                            @RequestParam("user") String user,
                                            @RequestParam("password") String password) {
        RestTemplate restTemplate = restTemplateFactory.getObject();
        restTemplate.getInterceptors().add(
                new BasicAuthorizationInterceptor(user, password));

        MultiValueMap<String, String> credentials = new LinkedMultiValueMap();
        credentials.add("username", "admin");
        credentials.add("password", "admin");
        credentials.add("grant_type", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity body = new HttpEntity(credentials, headers);

        return restTemplate.exchange(
                target,
                HttpMethod.POST, body, String.class); //TODO


    }

    @RequestMapping(value="/resource", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<? extends Serializable> getResource(@RequestParam("target") String target,
                                                              @RequestParam("token") String token) {
        RestTemplate restTemplate = restTemplateFactory.getObject();
        restTemplate.getInterceptors().clear();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", Arrays.asList("Bearer " + token));
        HttpEntity body = new HttpEntity(headers);
        return restTemplate.exchange(
                target,
                HttpMethod.GET, body, String.class); //TODO


    }
}
