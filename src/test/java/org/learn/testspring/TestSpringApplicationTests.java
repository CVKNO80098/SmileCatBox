package org.learn.testspring;

import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.learn.testspring.mapper")
public class TestSpringApplicationTests {

    @Test
    public void testFun() {
        System.out.println("ok");
    }

}
