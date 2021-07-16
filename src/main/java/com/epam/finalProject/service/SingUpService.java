package com.epam.finalProject.service;

import com.epam.finalProject.entity.User;
import com.epam.finalProject.exception.AppException;

public interface SingUpService {
    User insertUserToDB(String username,String password,String name,String surname, String lang) throws AppException;

}
