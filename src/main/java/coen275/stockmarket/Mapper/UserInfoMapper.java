package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Component("userInfoMapper")
public interface UserInfoMapper {
    @Update({
            "update userInfo",
            "set cash = #{cash, jdbcType=BIGINT} where userId = #{Id, jdbcType=BIGINT}"
    })
    int updateUserInfo(UserInfo userInfo);

    @Select(value = "select u.username,u.password from userinfo u where u.username=#{username}")
    @Results
            ({@Result(property = "username",column = "username"),
                    @Result(property = "password",column = "password")})
    UserInfo findUserByName(@Param("username") String username);

    //注册
    @Insert("insert into user values(#{userId},#{username},#{password})")
    //加入该注解可以保存对象后，查看对象插入id
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void regist(UserInfo user);

    //登录
    @Select("select u.id from user u where u.username = #{username} and password = #{password}")
    Long login(UserInfo user);

    @Select("select Id , userName, password, cash, userStocksInfoList from userInfo where Id = #{userId,jdbcType=BIGINT}")
    UserInfo getUserInfoService(Long userId);
}
