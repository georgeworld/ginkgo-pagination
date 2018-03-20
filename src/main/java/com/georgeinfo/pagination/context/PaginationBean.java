/*
 * A George software product.
 * Copyright (C) George (http://www.georgeinfo.com), All Rights Reserved..
 */
package com.georgeinfo.pagination.context;

import java.util.LinkedHashMap;

/**
 * 分页栏数据对象
 *
 * @author George <Georgeinfo@163.com>
 */
public class PaginationBean {

    private Long currentPageNo;
    private Long totalPages;
    private Long totalRecords;
    private Long pageSize;
    private String queryString;
    private PItem prePage;
    private PItem nextPage;
    private PItem lastPage;
    private PItem firstPage;
    private LinkedHashMap<Integer, PItem> pageItemMap;

    public PaginationBean() {
    }

    public Long getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(Long currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public PItem getPrePage() {
        return prePage;
    }

    public void setPrePage(PItem prePage) {
        this.prePage = prePage;
    }

    public PItem getNextPage() {
        return nextPage;
    }

    public void setNextPage(PItem nextPage) {
        this.nextPage = nextPage;
    }

    public PItem getLastPage() {
        return lastPage;
    }

    public void setLastPage(PItem lastPage) {
        this.lastPage = lastPage;
    }

    public PItem getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(PItem firstPage) {
        this.firstPage = firstPage;
    }

    public LinkedHashMap<Integer, PItem> getPageItemMap() {
        return pageItemMap;
    }

    public void setPageItemMap(LinkedHashMap<Integer, PItem> pageItemMap) {
        this.pageItemMap = pageItemMap;
    }

    @Override
    public String toString() {
        return "PaginationBean{" + "currentPageNo=" + currentPageNo + ", totalPages=" + totalPages + ", totalRecords=" + totalRecords + ", pageSize=" + pageSize + ", queryString=" + queryString + ", prePage=" + prePage + ", nextPage=" + nextPage + ", lastPage=" + lastPage + ", firstPage=" + firstPage + ", pageItemMap=" + pageItemMap + '}';
    }

}
