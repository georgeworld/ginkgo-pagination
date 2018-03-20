/*
 * A George software product.
 * Copyright (C) George (http://www.georgeinfo.com), All Rights Reserved..
 */
package com.georgeinfo.pagination.context;

/**
 * 分页链接实体类
 *
 * @author George <Georgeinfo@163.com>
 */
public class PItem {

    private String href;
    private boolean noLink = false;

    private PItem() {
    }

    public static PItem noLink() {
        PItem pi = new PItem();
        pi.setNoLink(true);
        return pi;
    }

    public static PItem link(String href) {
        PItem pi = new PItem();
        pi.setHref(href);
        return pi;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isNoLink() {
        return noLink;
    }

    public void setNoLink(boolean noLink) {
        this.noLink = noLink;
    }

    @Override
    public String toString() {
        return "PItem{" + "href=" + href + ", noLink=" + noLink + '}';
    }

}
