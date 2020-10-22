package com.geektime.druidlogslowsql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FooService {
    // select for update 加锁
    // 当select语句中使用了for update
    // 如果当前select发现自己的结果集中有一条或者多条数据正在被修改，那么再执行此语句就会一直等待

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void selectForUpdate() {
        jdbcTemplate.queryForObject("SELECT BAR FROM FOO WHERE ID = 1 FOR UPDATE", String.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
