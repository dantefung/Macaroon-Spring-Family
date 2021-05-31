package com.dantefung.springbootstatemachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: TODO
 * @Author: DANTE FUNG
 * @Date:2019/8/28 17:22
 */
@SpringBootTest(classes = SpringBootStatemachineApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("testunit")
@Slf4j
public class Tester {

}
