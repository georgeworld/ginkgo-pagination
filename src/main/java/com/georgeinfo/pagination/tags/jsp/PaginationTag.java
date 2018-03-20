/*
 * Programming by: George <GeorgeNiceWorld@gmail.com>
 * Copyright (C) George And George Companies to Work For, All Rights Reserved.
 */
package com.georgeinfo.pagination.tags.jsp;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.georgeinfo.base.util.logger.GeorgeLogger;
import gbt.config.GeorgeLoggerFactory;
import com.georgeinfo.pagination.context.GenericPagingContext;

/**
 * 传统Html分页标签
 *
 * @author George <GeorgeNiceWorld@gmail.com>
 */
public class PaginationTag extends TagSupport {

    private final GeorgeLogger logger = GeorgeLoggerFactory.getLogger(getClass());
    private static final long serialVersionUID = 1L;
    private static final String PAGINATION_BAR_STYLE_DEFAULT_VALUE = "defaultStyle";
    private GenericPagingContext data;
    private String cssClass;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = super.pageContext.getOut();
        try {
            if (data != null) {
                if (cssClass == null || cssClass.isEmpty()) {
                    cssClass = PAGINATION_BAR_STYLE_DEFAULT_VALUE;
                }

                String paginationBarHtml = renderPagingBar();

                out.write(paginationBarHtml);
            } else {
                out.write("分页数据为空");
            }
        } catch (IOException e) {
            logger.error("分页错误：" + e.getMessage(), e);
        }

        return super.doStartTag();
    }

    /**
     * 分页视图显示代码
     *
     * @return the Paging Html
     */
    public String renderPagingBar() {
        //拼装queryString 开始
        String queryString = data.getParamsQueryString();
        if (queryString != null && !queryString.trim().isEmpty()) {
            if (queryString.indexOf("pageSize=") > -1) {
                queryString = queryString.replace("pageSize=", "");
            }
            if (queryString.indexOf("totalRecords=") > -1) {
                queryString = queryString.replace("totalRecords=", "");
            }
            if (queryString.indexOf("pageNo=") > -1) {
                queryString = queryString.replace("pageNo=", "");
            }
        }
        queryString = "?pageSize=" + data.getPageSize() + "&totalRecords=" + data.getTotalRecords()
                + "&" + queryString;
        //拼装queryString 结束

        StringBuffer html = new StringBuffer();
        html.append("<div class=\"paginationActions\">");
        html.append("<div class=\"pagination\">");
        html.append("<span class=\"infoLabel\">");
        html.append("Total Records：").append(data.getTotalRecords());
        html.append("&nbsp;&nbsp; Page:");
        html.append("[").append(data.getCurrentPageNo()).append(" of ").append(data.getTotalPages()).append("]");
        html.append("</span> \n");
        if (data.getCurrentPageNo() == 1) {
            html.append("<span class=\"disabled prev_page\">首页</span> \n");
        } else {
            html.append("<a href=\"").append(queryString).append("&pageNo=1\">首页</a> \n");
        }
        if (data.getCurrentPageNo() <= data.getTotalPages() && data.getCurrentPageNo() > 1) {
            html.append("<a href=\"").append(queryString).append("&pageNo=")
                    .append(data.getCurrentPageNo() - 1)
                    .append("\" rel=\"next\">&laquo; 上一页</a> \n");
        } else {
            html.append("<span class=\"disabled prev_page\">&laquo; 上一页</span> \n");
        }
        if (data.getTotalPages() >= 9) {
            if (data.getCurrentPageNo() >= 1 && data.getCurrentPageNo() < 4) {//如果当前页码在1到4之间
                for (int index = 1; index <= 9; index++) {
                    if (index == data.getCurrentPageNo()) {
                        html.append("<span class=\"current\">")
                                .append(data.getCurrentPageNo())
                                .append("</span> \n");
                    } else {
                        html.append("<a href=\"")
                                .append(queryString)
                                .append("&pageNo=")
                                .append(index)
                                .append("\">")
                                .append(index)
                                .append("</a> \n");
                    }
                }
            } else if (data.getCurrentPageNo() >= 4 && data.getCurrentPageNo() <= (data.getTotalPages() - 5)) {
                for (int index = (int) (data.getCurrentPageNo().longValue() - 3); index <= data.getCurrentPageNo().longValue() + 5; index++) {
                    if (index == data.getCurrentPageNo()) {
                        html.append("<span class=\"current\">")
                                .append(data.getCurrentPageNo())
                                .append("</span> \n");
                    } else {
                        html.append("<a href=\"")
                                .append(queryString)
                                .append("&pageNo=")
                                .append(index)
                                .append("\">")
                                .append(index)
                                .append("</a> \n");
                    }
                }
            } else if (data.getCurrentPageNo() > (data.getTotalPages() - 5)) {
                for (int index = (int) (data.getTotalPages().longValue() - 8); index <= data.getTotalPages().longValue(); index++) {
                    if (index == data.getCurrentPageNo()) {
                        html.append("<span class=\"current\">")
                                .append(data.getCurrentPageNo())
                                .append("</span> \n");
                    } else {
                        html.append("<a href=\"")
                                .append(queryString)
                                .append("&pageNo=")
                                .append(index)
                                .append("\">")
                                .append(index)
                                .append("</a> \n");
                    }
                }
            }
        } else {
            for (int index = 1; index <= data.getTotalPages(); index++) {
                if (index == data.getCurrentPageNo()) {
                    html.append("<span class=\"current\">").append(data.getCurrentPageNo()).append("</span> \n");
                } else {
                    html.append("<a href=\"").append(queryString).append("&pageNo=").append(index).append("\">").append(index).append("</a> \n");
                }
            }
        }
        //html.append("<span class=\"gap\">&hellip;</span> \n");
        if (data.getCurrentPageNo() < data.getTotalPages()) {
            html.append("<a href=\"")
                    .append(queryString)
                    .append("&pageNo=")
                    .append(data.getCurrentPageNo() + 1)
                    .append("\" class=\"next_page\" rel=\"next\">下一页 &raquo;</a>\n");
        } else {
            html.append("<span class=\"disabled next_page\">下一页 &raquo;</span> \n");
        }
        if (data.getCurrentPageNo() < data.getTotalPages()) {
            html.append("<a href=\"")
                    .append(queryString)
                    .append("&pageNo=")
                    .append(data.getTotalPages())
                    .append("\" class=\"next_page\" rel=\"next\">尾页</a>\n");
        } else {
            html.append("<span class=\"disabled next_page\">尾页</span> \n");
        }
        html.append("</div>\n");
        html.append("</div>");

        return html.toString();
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public GenericPagingContext getData() {
        return data;
    }

    public void setData(GenericPagingContext data) {
        this.data = data;
    }
}
