package com.vbashur.mockclient.controller;


import com.vbashur.mockclient.ClientMainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ClientMainApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientDefaultRestControllerIntegrationTest {

    @Autowired
    ClientDefaultRestController controller;

    @Test
    public void testAuthSuccess() {
//TODO fetch URL and credentials from cfg server
        controller.auth("http://localhost:8091/auth/index", "admin", "admin");
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
