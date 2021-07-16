package com.epam.finalProject.service;

import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.exception.AppException;

public interface AdminService {
    boolean removeItem(long goodsParamId) throws AppException;
    boolean createItem(CatalogItem catalogItem) throws AppException;
    boolean updateItem(CatalogItem catalogItem) throws AppException;
}
