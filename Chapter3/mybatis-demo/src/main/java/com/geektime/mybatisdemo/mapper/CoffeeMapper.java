package com.geektime.mybatisdemo.mapper;

import com.geektime.mybatisdemo.model.Coffee;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CoffeeMapper {

    int save(Coffee coffee);

    @Select("select * from t_coffee where id = #{id}")
    @Results({
            @Result(id=true,column = "id",property = "id")
    })
    Coffee findById(@Param("id") Long id);
}
