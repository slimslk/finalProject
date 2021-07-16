package com.epam.finalProject.service;

import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.exception.AppException;

import java.util.List;

public interface CatalogService {
    List<CatalogItem> getListOfItems(List<Long> listId) throws AppException;
    int getCountOfGoods() throws AppException;
}
