package com.geektime.transactionpropagationdemo;

public interface FooService {
    void insertThenRollback() throws RollbackException;

    void invokeInsertThenRollback();
}
