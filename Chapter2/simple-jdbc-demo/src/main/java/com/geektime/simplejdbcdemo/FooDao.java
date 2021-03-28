package com.geektime.simplejdbcdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class FooDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    public void insertData() {
        String sql = "INSERT INTO FOO (BAR) VALUES(?)";
        Arrays.asList("bbb", "ccc").forEach(bar -> {
            jdbcTemplate.update(sql, bar);
        });

        Map<String, String> row = new HashMap<>();
        row.put("BAR", "ddd");
        Number id = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID of ddd:{}", id.longValue());
    }

    public void listData() {
        log.info("Count:{}",
                jdbcTemplate.queryForList("SELECT COUNT(*) FROM FOO", Long.class));

        List<String> list = jdbcTemplate.queryForList("SELECT BAR FROM FOO", String.class);
        list.forEach(bar -> {
            log.info("Bar:{}", bar);
        });

        List<Foo> fooList = jdbcTemplate.query("SELECT * FROM FOO",
                (resultSet, i) -> Foo.builder()
                        .id(resultSet.getLong(1))
                        .bar(resultSet.getString(2))
                        .build());

        fooList.forEach(foo -> log.info("Foo:{}", foo));
    }
}
