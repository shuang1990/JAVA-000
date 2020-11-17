package org.javaboy.usemystarter;

import org.javaboy.mystarter.HelloService;
import org.javaboy.mystarter.Klass;
import org.javaboy.mystarter.School;
import org.javaboy.mystarter.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsemystarterApplicationTests {

    @Autowired
    HelloService helloService;

    @Autowired
    School school;

    @Autowired
    @Qualifier("student100")
    Student student;

    @Autowired
    Klass klass;

    @Test
    public void contextLoads() {
        System.out.println(helloService.sayHello());
        school.ding();
        System.out.println(school.getStudent100());
        school.getClass1().dong();
        System.out.println(student.getId());
        klass.dong();
    }

}
