package com.vbashur.mockclient.controller;


import com.vbashur.mockclient.ClientMainApplication;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.io.Serializable;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ClientMainApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientDefaultRestControllerIntegrationTest {

    @Autowired
    ClientDefaultRestController controller;

    @Test
    public void testAuthSuccess() {
//TODO fetch URL and credentials from cfg server
        ResponseEntity<? extends Serializable> response =  controller.auth("http://localhost:8091/auth/oauth/token", "maingateway", "");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("BODY: " + response.getBody().toString());
        System.out.println("HEADERS: " + response.getHeaders().toString());
        JacksonJsonParser parser= new JacksonJsonParser();
        Map<String, Object> jwtMap = parser.parseMap(response.getBody().toString());
        String accessToken = jwtMap.get("access_token").toString();
        System.out.println("TOKEN: " + jwtMap.get("access_token").toString());
        Assert.assertNotNull(accessToken);


        response =  controller.getResource("http://localhost:8092/account/2", accessToken);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("BODY: " + response.getBody().toString());
        System.out.println("HEADERS: " + response.getHeaders().toString());



    }

    @Test(expected = HttpClientErrorException.class)
    public void testAuthFailure() {
//TODO fetch URL and credentials from cfg server
        controller.auth("http://localhost:8091/auth/index", "adminDummy", "adminDummy");
    }

    @Test
    public void testMainGatewaySuccess() {
//TODO fetch URL and credentials from cfg server
        controller.auth("http://localhost:8080/index", "admin", "admin");
    }

    @Test(expected = HttpClientErrorException.class)
    public void testMainGatewayFailure() {
//TODO fetch URL and credentials from cfg server
        controller.auth("http://localhost:8080/index", "adminDummy", "adminDummy");
    }
}
