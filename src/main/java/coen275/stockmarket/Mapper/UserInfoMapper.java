package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.UserInfo;
import coen275.stockmarket.data.UserInfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Mapper
@Component("userInfoMapper")
public interface UserInfoMapper{


    @Delete({
        "delete from user_info",
        "where userId = #{userId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long userId);

    @Insert({
        "insert into user_info (userId, username, ",
        "password, cash)",
        "values (#{userId,jdbcType=BIGINT}, #{username,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{cash,jdbcType=DOUBLE})"
    })
    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    @Select({
        "select",
        "userId, username, password, cash",
        "from user_info",
        "where userId = #{useId,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    UserInfo selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserInfo record);

    @Update({
        "update user_info",
        "set username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "cash = #{cash,jdbcType=DOUBLE}",
        "where userId = #{userId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserInfo record);

    @Update({
            "update user_info",
            "set cash = #{cash, jdbcType=BIGINT} where userId = #{userId, jdbcType=BIGINT}"
    })
    int updateUserInfo(UserInfo userInfo);

    @Select(value = "select u.username,u.password from user_info u where u.username=#{username}")
    UserInfo findUserByName(@Param("username") String username);

    //注册
    @Insert({
            "insert into user_info (username, password)",
            "values (#{username,jdbcType=VARCHAR}, ",
            "#{password,jdbcType=VARCHAR})"
    })
    //加入该注解可以保存对象后，查看对象插入id
    void register(String username, String password);

    //登录
    @Select("select u.userId from user_info u where u.username = #{username} and password = #{password}")
    Long login(String username, String password);

    @Select("select userId , username, password, cash from user_info where userId = #{userId,jdbcType=BIGINT}")
    UserInfo getUserInfoService(@Param("userId") Long userId);

    @Update({
            "UPDATE user_info SET cash = cash + #{cash,jdbcType=DOUBLE} WHERE userId = #{userId,jdbcType=BIGINT}"
    })
    void updateUserInfoCash(@Param("cash") Double cash, @Param("userId") Long userId);

}