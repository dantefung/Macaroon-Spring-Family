package com.dantefung.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author DANTE FUNG
 * @version 1.0
 * @description 单元测试父类
 * @date 2019/10/16
 */
@SpringBootTest(classes = RocketmqProducerApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("testunit")
@Slf4j
public class Tester {
}
