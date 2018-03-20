/*
 * Programming by: George <GeorgeNiceWorld@gmail.com>
 * Copyright (C) George And George Companies to Work For, All Rights Reserved.
 */
package com.georgeinfo.pagination.tags.jsp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.georgeinfo.base.util.logger.GeorgeLogger;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.georgeinfo.pagination.context.GenericPagingContext;
import com.georgeinfo.pagination.context.PItem;
import com.georgeinfo.pagination.context.PaginationBean;
import gbt.config.GeorgeLoggerFactory;
import java.util.LinkedHashMap;

/**
 * Json分页标签
 *
 * @author George <GeorgeNiceWorld@gmail.com>
 */
public class JsonPaginationTag extends TagSupport {

    public static final String PAGINATION_BAR_VAR_DEFAULT_VALUE = "paginationBar";
    private static final GeorgeLogger LOG = GeorgeLoggerFactory.getLogger(JsonPaginationTag.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final long serialVersionUID = 1L;
    private static final String BOOTSTRAP_RENDERER = "bootstrap";
    private GenericPagingContext data;
    private String varName;
    private String renderer;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = super.pageContext.getOut();
        try {
            if (data != null) {
                if (varName == null || varName.isEmpty()) {
                    varName = PAGINATION_BAR_VAR_DEFAULT_VALUE;
                }

                String jsonData = buildPagingData();
                String rendererScript = "";
                String isCallPagingRenderer = "var isCall_"+varName+"_Renderer = false;";
                if (renderer != null && !renderer.trim().isEmpty()) {
                    if (renderer.trim().equals(BOOTSTRAP_RENDERER)) {
                        rendererScript = bootstrapResolver();
                        isCallPagingRenderer = "var isCall_"+varName+"_Renderer = true;";
                    }
                }
                String html = "<div id=\"" + varName + "_bar\"></div>\n<script type=\"text/javascript\">\n var " + varName + "_Data = " + jsonData + ";\n"
                        + isCallPagingRenderer +"\n"+ rendererScript + "\n</script>";
                out.write(html);
            } else {
                out.write("分页数据为空");
            }
        } catch (IOException e) {
            LOG.error("分页错误：" + e.getMessage(), e);
        }

        return super.doStartTag();
    }

    /**
     * 分页视图显示代码
     *
     * @return the Paging Html
     */
    public String buildPagingData() {
        PaginationBean pb = new PaginationBean();
        pb.setTotalPages(data.getTotalPages());
        pb.setTotalRecords(data.getTotalRecords());
        pb.setCurrentPageNo(data.getCurrentPageNo());
        pb.setPageSize(data.getPageSize());

        //拼装queryString 开始
        String queryString = data.getParamsQueryString();
        if (queryString != null && !queryString.trim().isEmpty()) {
            if (queryString.contains("pageSize=")) {
                queryString = queryString.replace("pageSize=", "");
            }
            if (queryString.contains("totalRecords=")) {
                queryString = queryString.replace("totalRecords=", "");
            }
            if (queryString.contains("pageNo=")) {
                queryString = queryString.replace("pageNo=", "");
            }
        }
        queryString = "?pageSize=" + data.getPageSize() + "&totalRecords=" + data.getTotalRecords()
                + "&" + queryString;

        pb.setQueryString(queryString);
        //拼装queryString 结束

        if (data.getCurrentPageNo() == 1) {
            pb.setFirstPage(PItem.noLink());
        } else {
            pb.setFirstPage(PItem.link(queryString + "&pageNo=1")); //NEW
        }
        if (data.getCurrentPageNo() <= data.getTotalPages() && data.getCurrentPageNo() > 1) {
            pb.setPrePage(PItem.link(queryString + "&pageNo=" + (data.getCurrentPageNo() - 1)));  //NEW
        } else {
            pb.setPrePage(PItem.noLink());
        }

        LinkedHashMap<Integer, PItem> pageItemMap = new LinkedHashMap<Integer, PItem>();
        if (data.getTotalPages() >= 9) {
            if (data.getCurrentPageNo() >= 1 && data.getCurrentPageNo() < 4) {//如果当前页码在1到4之间
                for (int index = 1; index <= 9; index++) {
                    if (index == data.getCurrentPageNo()) {
                        pageItemMap.put(index, PItem.noLink());  //NEW
                    } else {
                        pageItemMap.put(index, PItem.link(queryString + "&pageNo=" + index));  //NEW
                    }

                }
            } else if (data.getCurrentPageNo() >= 4 && data.getCurrentPageNo() <= (data.getTotalPages() - 5)) {
                for (int index = (int) (data.getCurrentPageNo().longValue() - 3); index <= data.getCurrentPageNo().longValue() + 5; index++) {
                    if (index == data.getCurrentPageNo()) {
                        pageItemMap.put(index, PItem.noLink());  //NEW
                    } else {
                        pageItemMap.put(index, PItem.link(queryString + "&pageNo=" + index));  //NEW
                    }

                }
            } else if (data.getCurrentPageNo() > (data.getTotalPages() - 5)) {
                for (int index = (int) (data.getTotalPages().longValue() - 8); index <= data.getTotalPages().longValue(); index++) {
                    if (index == data.getCurrentPageNo()) {
                        pageItemMap.put(index, PItem.noLink());  //NEW
                    } else {
                        pageItemMap.put(index, PItem.link(queryString + "&pageNo=" + index));  //NEW
                    }

                }
            }
        } else {
            for (int index = 1; index <= data.getTotalPages(); index++) {
                if (index == data.getCurrentPageNo()) {
                    pageItemMap.put(index, PItem.noLink());  //NEW
                } else {
                    pageItemMap.put(index, PItem.link(queryString + "&pageNo=" + index));  //NEW
                }
            }
        }

        pb.setPageItemMap(pageItemMap);

        if (data.getCurrentPageNo() < data.getTotalPages()) {
            pb.setNextPage(PItem.link(queryString + "&pageNo=" + (data.getCurrentPageNo() + 1)));  //NEW
        } else {
            pb.setNextPage(PItem.noLink());
        }

        if (data.getCurrentPageNo() < data.getTotalPages()) {
            pb.setLastPage(PItem.link(queryString + "&pageNo=" + data.getTotalPages()));  //NEW
        } else {
            pb.setLastPage(PItem.noLink());
        }

        try {
            String json = MAPPER.writeValueAsString(pb);
            return json;
        } catch (JsonProcessingException ex) {
            LOG.error("### Exception when write json value from object.", ex);
            return null;
        }
    }

    public String bootstrapResolver() {
        StringBuffer script = new StringBuffer("function " + varName + "_func(){");
        script.append("try{");
        script.append("if(isCall_").append(varName).append("_Renderer === true){");
        script.append("var paginationBar_Element = document.getElementById(\"").append(varName).append("_bar\");");
        script.append("if(paginationBar_Data  !== null && paginationBar_Element !== null){");

        script.append("var paging_bar_hrml = '<ul class=\"pagination\">';");
        script.append("paging_bar_hrml = paging_bar_hrml + '<li><a href=\"javascript:void(0);\" title=\"记录数\" data-container=\"body\" data-toggle=\"popover\" data-placement=\"top\" ");
        script.append("data-content=\"记录总数：'+paginationBar_Data.totalRecords+'，总页数：'+paginationBar_Data.totalPages+'，每页显示：'+paginationBar_Data.pageSize+'条记录\">'+paginationBar_Data.totalRecords+'</a></li>';");

        script.append("if (paginationBar_Data.firstPage.noLink === true) {");
        script.append("paging_bar_hrml = paging_bar_hrml + '<li class=\"disabled\"><a href=\"javascript:void(0);\">首页</a></li>';");
        script.append("} else {");
        script.append("paging_bar_hrml = paging_bar_hrml + '<li><a href=\"'+paginationBar_Data.firstPage.href+'\">首页</a></li>';");
        script.append("}");

        script.append("if (paginationBar_Data.prePage.noLink === true) {");
        script.append("paging_bar_hrml = paging_bar_hrml + '<li class=\"disabled\"><a href=\"javascript:void(0);\">&laquo;</a></li>';");
        script.append("} else {");
        script.append("paging_bar_hrml = paging_bar_hrml + '<li><a href=\"'+paginationBar_Data.prePage.href+'\">&laquo;</a></li>';");
        script.append("}");

        script.append("for(var index in paginationBar_Data.pageItemMap ){");
        script.append("if(paginationBar_Data.pageItemMap[index].noLink === true){");
        script.append("paging_bar_hrml = paging_bar_hrml + '<li class=\"active\"><a href=\"#\">'+index+'</a></li>';");
        script.append("}else{");
        script.append("paging_bar_hrml = paging_bar_hrml + '<li><a href=\"'+paginationBar_Data.pageItemMap[index].href+'\">'+index+'</a></li>';");
        script.append("}");
        script.append("}");

        script.append("if (paginationBar_Data.nextPage.noLink === true) {");
        script.append("paging_bar_hrml = paging_bar_hrml + '<li class=\"disabled\"><a href=\"javascript:void(0);\">&raquo;</a></li>';");
        script.append("} else {");
        script.append("paging_bar_hrml = paging_bar_hrml + '<li><a href=\"'+paginationBar_Data.nextPage.href+'\">&raquo;</a></li>';");
        script.append("}");

        script.append("if (paginationBar_Data.lastPage.noLink === true) {");
        script.append("paging_bar_hrml = paging_bar_hrml + '<li class=\"disabled\"><a href=\"javascript:void(0);\">尾页</a></li>';");
        script.append("} else {");
        script.append("paging_bar_hrml = paging_bar_hrml + '<li><a href=\"'+paginationBar_Data.lastPage.href+'\">尾页</a></li>';");
        script.append("}");

        script.append("paging_bar_hrml = paging_bar_hrml + '</ul>'; ");

        script.append("paginationBar_Element.innerHTML = paging_bar_hrml;");
        script.append("}}");
        script.append("}catch(e){alert(e);}");
        script.append("}");

        return script.toString();
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public GenericPagingContext getData() {
        return data;
    }

    public void setData(GenericPagingContext data) {
        this.data = data;
    }

    public String getRenderer() {
        return renderer;
    }

    public void setRenderer(String renderer) {
        this.renderer = renderer;
    }

}
