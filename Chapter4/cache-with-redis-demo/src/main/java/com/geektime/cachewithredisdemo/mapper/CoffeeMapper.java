package com.geektime.cachewithredisdemo.mapper;

import com.geektime.cachewithredisdemo.model.Coffee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CoffeeMapper {

    @Select("select * from t_coffee")
    List<Coffee> findAllCoffee();
}