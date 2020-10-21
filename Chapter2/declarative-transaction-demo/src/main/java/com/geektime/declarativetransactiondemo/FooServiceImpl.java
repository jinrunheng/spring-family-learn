package com.geektime.declarativetransactiondemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FooServiceImpl implements FooService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO(BAR) VALUES('AAA')");
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO(BAR) VALUES('BBB')");
        throw new RollbackException();
    }

    @Override
    public void invokeInsertThenRollback() throws RollbackException {
        // 不会发生回滚
        // 因为invokeInsertThenRollback这个方法就没有添加事务 所以在执行了insertThenRollback之后就直接commit了
        // 因此并不会发生rollback

        insertThenRollback();
    }
}
