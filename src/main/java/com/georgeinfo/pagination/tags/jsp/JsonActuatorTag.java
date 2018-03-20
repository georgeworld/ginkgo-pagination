/*
 * Programming by: George <GeorgeNiceWorld@gmail.com>
 * Copyright (C) George And George Companies to Work For, All Rights Reserved.
 */
package com.georgeinfo.pagination.tags.jsp;

import com.georgeinfo.base.util.logger.GeorgeLogger;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import gbt.config.GeorgeLoggerFactory;

/**
 * Json分页执行器标签
 *
 * @author George <GeorgeNiceWorld@gmail.com>
 */
public class JsonActuatorTag extends TagSupport {

    private static final GeorgeLogger LOG = GeorgeLoggerFactory.getLogger(JsonActuatorTag.class);
    private static final long serialVersionUID = 1L;
    private String varName;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = super.pageContext.getOut();
        try {
            if (varName == null || varName.isEmpty()) {
                varName = JsonPaginationTag.PAGINATION_BAR_VAR_DEFAULT_VALUE;
            }

            String html = varName + "_func();";
            out.write(html);
        } catch (IOException e) {
            LOG.error("分页解析错误：" + e.getMessage(), e);
        }

        return super.doStartTag();
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
}
