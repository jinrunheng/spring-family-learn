package com.geektime.errorcodedemo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ErrorcodeDemoApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testThrowingCustomException() {
        try {
            jdbcTemplate.execute("INSERT INTO FOO (ID,BAR) VALUES (1,'aaa')");
            jdbcTemplate.execute("INSERT INTO FOO (ID,BAR) VALUES (1,'bbb')");
        } catch (Exception e) {
            Assertions.assertThatExceptionOfType(CustomDuplicatedKeyException.class);
        }
    }
}
