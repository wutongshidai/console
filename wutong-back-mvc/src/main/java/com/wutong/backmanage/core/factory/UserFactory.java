package com.wutong.backmanage.core.factory;

import org.springframework.beans.BeanUtils;

import com.wutong.backmanage.pojo.User;
import com.wutong.backmanage.core.transfer.UserDto;

/**
 * 用户创建工厂
 *
 */
public class UserFactory {

    public static User createUser(UserDto userDto){
        if(userDto == null){
            return null;
        }else{
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            return user;
        }
    }
}
