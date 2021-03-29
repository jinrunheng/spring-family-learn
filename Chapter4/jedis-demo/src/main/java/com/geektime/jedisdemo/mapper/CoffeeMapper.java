package com.geektime.jedisdemo.mapper;

import com.geektime.jedisdemo.model.Coffee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CoffeeMapper {
    @Select("select * from t_coffee")
    List<Coffee> findAllCoffee();
}
