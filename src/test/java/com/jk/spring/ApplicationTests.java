package com.jk.spring;

import com.jk.spring.service.UseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private UseService useService;

    @Test
    public void contextLoads() {
        useService.useInterface();
        useService.useStartegy();
        useService.useOverLoadingAndRiding("J Tag?");
        useService.userIOC_DI();
    }

}
