package com.vbashur.mockclient.controller;


import com.vbashur.mockclient.ClientMainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ClientMainApplication.class})
public class ClientDefaultRestControllerIntegrationTest {

    @Autowired
    ClientDefaultRestController controller;

    @Test
    public void testAuth() {
//TODO fetch URL and credentials from cfg server
        controller.auth("http://localhost:8091/auth/index", "admin", "admin");
    }
}
