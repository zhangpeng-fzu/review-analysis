package com.peng.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/17.
 */
public class ReplyEntity {

    private int total;
    private int watershed;
    private int maxPage;
    private String search;
    private int currentPageNum;
    private List<Map<String,Object>> comments;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getWatershed() {
        return watershed;
    }

    public void setWatershed(int watershed) {
        this.watershed = watershed;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public List<Map<String, Object>> getComments() {
        return comments;
    }

    public void setComments(List<Map<String, Object>> comments) {
        this.comments = comments;
    }
}
