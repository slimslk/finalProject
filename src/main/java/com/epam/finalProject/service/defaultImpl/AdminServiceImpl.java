package com.epam.finalProject.service.defaultImpl;

import com.epam.finalProject.database.impl.ChangeGoodsDAOImpl;
import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.AdminService;

public class AdminServiceImpl implements AdminService {
    private ChangeGoodsDAOImpl changeGoodsDAOImpl =new ChangeGoodsDAOImpl();

    @Override
    public boolean createItem(CatalogItem catalogItem) throws AppException {
        changeGoodsDAOImpl.createGoods(catalogItem);
        return true;
    }

    @Override
    public boolean updateItem(CatalogItem catalogItem) throws AppException {
        changeGoodsDAOImpl.updateGoods(catalogItem);
        return true;
    }

    @Override
    public boolean removeItem(long goodsParamId) throws AppException {
        changeGoodsDAOImpl.removeGoods(goodsParamId);
        return true;
    }
}
