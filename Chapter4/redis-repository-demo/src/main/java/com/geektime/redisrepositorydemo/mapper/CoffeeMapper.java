package com.geektime.redisrepositorydemo.mapper;

import com.geektime.redisrepositorydemo.model.Coffee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface CoffeeMapper {

    @Select("select * from t_coffee where name = #{name}")
    Coffee findCoffeeByName(@Param("name") String name);
}
