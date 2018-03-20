package com.georgeinfo.pagination.context;

import java.util.HashMap;
import java.util.List;

public interface GenericPagingContext {

    public static final Long DEFAULT_PAGE_SIZE = 16L;

    /**
     * 初始化分页上下文环境
     */
    public void init();

    public Long getTotalRecords();

    public void setTotalRecords(Long totalRecords);

    public Long getCurrentPageNo();

    public void setCurrentPageNo(Long currentPageNo);

    public Long getPageSize();

    public void setPageSize(Long pageSize);

    public void setTotalPages(Long totalPages);

    public Long getTotalPages();

    public String getBaseUri();

    public void setBaseUri(String baseUri);

    public HashMap<String, Object> getParams();

    public void setParams(HashMap<String, Object> params);

    /**
     * 得到url的参数字符串部分，类似“paramA=123&viewType=hello&dateTime=2010-06-18 11:12:10”，
     * 返回值不是以&作为开始和结束字符
     *
     * @return URL查询参数串
     */
    public String getParamsQueryString();

    public String getBaseUriWithQueryString();

    public String getSql();

    public void setSql(String sql);

    public String getCountSql();

    public void setCountSql(String countSql);

    public String getFinalSQL();

    public void setFinalSQL(String finalSQL);

    public HashMap<String, Object> getFinalParams();

    public void setFinalParams(HashMap<String, Object> finalParams);

    public <T> List<T> getRecordList();

    public void setRecordList(List recordList);
}
