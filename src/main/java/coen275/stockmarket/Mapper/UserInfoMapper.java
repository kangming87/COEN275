package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.UserInfo;
import org.apache.ibatis.annotations.*;

public interface UserInfoMapper {
    @Update({

    })
    int updateUserInfo(UserInfo userInfo);
}
