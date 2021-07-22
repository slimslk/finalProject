package com.epam.finalProject.database;

import com.epam.finalProject.entity.GoodsParam;
import com.epam.finalProject.exception.AppException;

public interface GoodsParamDAO {
    GoodsParam getGoodsParamByID(long id) throws AppException;
}
