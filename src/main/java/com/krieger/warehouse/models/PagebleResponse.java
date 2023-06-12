package com.krieger.warehouse.models;

public class PagebleResponse<T> {
    private T entries;
    private int pageNum = 0;
    private int totalPage = 0;
    private int pageSize = 0;
}
