package com.wutong.backmanage.core.page;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * 分页结果的封装(for Bootstrap Table)
 *
 */
public class PageInfoBT<T> {

    private long pageNumber, pageSize;

    // 结果集
    private List<T> rows;

    // 总数
    private long total;

    public PageInfoBT(List<T> page) {
        this.rows = page;
        if (page instanceof Page) {
            this.total = ((Page) page).getTotal();
        } else {
            this.total = page.size();
        }
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
