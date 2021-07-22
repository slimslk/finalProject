package com.epam.finalProject.database;

import com.epam.finalProject.exception.AppException;

public interface ParamDAO {
    String getParamName(String sqlExpresion, long id) throws AppException;
}
