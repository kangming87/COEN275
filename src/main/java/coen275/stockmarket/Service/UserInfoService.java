package coen275.stockmarket.Service;

import coen275.stockmarket.data.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {

    UserInfo getUserInfoService(Long userId);
}
