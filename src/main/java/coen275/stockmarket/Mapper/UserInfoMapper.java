package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.UserInfo;
import coen275.stockmarket.data.UserInfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

import org.apache.ibatis.annotations.*;


@Mapper
@Component("userInfoMapper")
public interface UserInfoMapper {
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
            "set cash = #{cash, jdbcType=BIGINT} where userId = #{Id, jdbcType=BIGINT}"
    })
    int updateUserInfo(UserInfo userInfo);

    @Select(value = "select u.username,u.password from user_info u where u.username=#{username}")
    @Results
            ({@Result(property = "username",column = "username"),
                    @Result(property = "password",column = "password")})
    UserInfo findUserByName(@Param("username") String username);

    //注册
    @Insert("insert into user_info values(#{userId},#{username},#{password})")
    //加入该注解可以保存对象后，查看对象插入id
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void regist(UserInfo user);

    //登录
    @Select("select u.id from user u where u.username = #{username} and password = #{password}")
    Long login(UserInfo user);

    @Select("select Id , userName, password, cash, userStocksInfoList from user_info where Id = #{userId,jdbcType=BIGINT}")
    UserInfo getUserInfoService(Long userId);
}