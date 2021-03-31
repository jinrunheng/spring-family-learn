package com.geektime.redisdemo.mapper;

import com.geektime.redisdemo.model.Coffee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CoffeeMapper {

    @Select("select * from t_coffee")
    List<Coffee> findAllCoffee();

    @Select("select * from t_coffee where name = #{name}")
    Coffee findByName(@Param("name") String name);
}