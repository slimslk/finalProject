package com.epam.finalProject.database;

import com.epam.finalProject.entity.Goods;
import com.epam.finalProject.exception.AppException;

public interface GoodsDAO {
    Goods getGoodsById(long id) throws AppException;
}
