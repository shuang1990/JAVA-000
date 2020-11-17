package org.javaboy.mystarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
@EnableConfigurationProperties({HelloProperties.class, Student.class})
@ConditionalOnClass(School.class)
public class MyStarterAutoConfiguration {
    @Autowired
    HelloProperties helloProperties;

    @Autowired
    Student student;

    @Bean
    School newSchool() {
        School school = new School();
        school.setStudent100(student);
        return school;
    }

    @Bean
    Klass klass() {
        Klass klass = new Klass();
        klass.setStudents(Arrays.asList(student));
        return klass;
    }

    @Bean("student100")
    Student student() {
        return student;
    }

    @Bean
    HelloService helloService() {
        HelloService helloService = new HelloService();
        helloService.setName(helloProperties.getName());
        helloService.setMsg(helloProperties.getMsg());
        return helloService;
    }
}
