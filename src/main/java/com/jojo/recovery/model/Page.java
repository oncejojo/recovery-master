package com.jojo.recovery.model;

/**
 * @author jojo
 */
public class Page {
    private Integer page = 1;

    private Integer rows = 15;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
