package com.geektime.mybatisdemo.mapper;

import com.geektime.mybatisdemo.model.Coffee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CoffeeMapper {

    int save(Coffee coffee);

    @Select("select * from t_coffee where id = #{id}")
    Coffee findById(@Param("id") Long id);
}
