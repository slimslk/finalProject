package com.epam.finalProject.database;

import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.exception.AppException;

public interface ChangeGoodsDAO {
    void removeGoods(long id) throws AppException;

    void updateGoods(CatalogItem catalogItem) throws AppException;

    void createGoods(CatalogItem catalogItem) throws AppException;
}
