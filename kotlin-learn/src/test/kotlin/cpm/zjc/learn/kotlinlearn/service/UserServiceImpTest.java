package cpm.zjc.learn.kotlinlearn.service;

import cpm.zjc.learn.kotlinlearn.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImpTest {

    @Autowired
    UserService userService;

    @Test
    public void findAll() {
        List<User> all = userService.findAll();
        System.out.println("----------second search:"+all);
    }
}
