package com.qj.common.model;

import java.util.List;

/**
 * @author qujun
 * @des
 * @time 2019/2/1 13:56
 * Because had because, so had so, since has become since, why say why。
 **/

public class WanInfo {

    private List<WanSubInfo> datas = null;

    private  int curPage = 0;
    private  int offset = 0;
    private  boolean over = false;
    private  int pageCount = 0;
    private  int size = 0;
    private  int total = 0;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<WanSubInfo> getDatas() {
        return datas;
    }

    public void setDatas(List<WanSubInfo> datas) {
        this.datas = datas;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
