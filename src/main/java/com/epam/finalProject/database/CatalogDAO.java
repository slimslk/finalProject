package com.epam.finalProject.database;

import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.exception.AppException;

import java.util.List;
import java.util.Map;

public interface CatalogDAO {
    int getCountOfGoods() throws AppException;

    Map<Integer, Object> getListOfSortedItems(String[] param, String sort, String dir, int start) throws AppException;

    int getCountOfItemsFromDB(Map<Integer, Object> stringMap) throws AppException;

    List<CatalogItem> getItemsFromDB(Map<Integer, Object> stringMap) throws AppException;

    CatalogItem getItemByGoodsId(long id) throws AppException;

    List<CatalogItem> getItemsByGoodsId(List<Long> list) throws AppException;

    void updateCatalogItem(CatalogItem catalogItem) throws AppException;
}
