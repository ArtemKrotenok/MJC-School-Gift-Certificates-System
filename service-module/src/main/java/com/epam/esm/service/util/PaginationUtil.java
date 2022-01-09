package com.epam.esm.service.util;

public class PaginationUtil {

    public static final int ITEMS_BY_PAGE = 10;

    public static int getPositionByPage(int page) {
        return ((page - 1) * ITEMS_BY_PAGE + 1) - 1;
    }
}
