package com.epam.finalProject.service.defaultImpl;

import com.epam.finalProject.database.CatalogDAO;
import com.epam.finalProject.entity.CatalogItem;
import com.epam.finalProject.entity.UserCart;
import com.epam.finalProject.exception.AppException;
import com.epam.finalProject.service.CatalogService;
import com.epam.finalProject.web.command.CommandAJAX;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CatalogServiceImpl implements CatalogService {
    private static final Logger log = LogManager.getLogger(CatalogServiceImpl.class);
    private CatalogDAO catalogDAO = new CatalogDAO();

    @Override
    public int getCountOfGoods() throws AppException {
        return catalogDAO.getCountOfGoods();
    }

    @Override
    public List<CatalogItem> getListOfItems(List<Long> listId) throws AppException {
        return catalogDAO.getItemsByGoodsId(listId);
    }
}
