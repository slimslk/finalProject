package com.epam.finalProject.database;

import com.epam.finalProject.entity.UserInfo;
import com.epam.finalProject.exception.AppException;

public interface UserInfoDAO {
    UserInfo getUserInfoById(long id) throws AppException;

    void insertUserInfo(UserInfo userInfo) throws AppException;
}
