package com.lsatin.topclass.basic.pojo;

/**
 * 分页器
 * <p>一个自定义分页器
 */
public final class Pagination<T> implements Cloneable {

    /** 总分页 */
    private Page total;
    /** 当前分页 */
    private Page current;
    /** 分页数据 */
    private T data;

    public Pagination() {
    }

    public Pagination(Integer currentPage, Integer currentSize, Integer totalSize) {
        this.current = new Page(currentPage, currentSize);
        this.total = new Page((int) Math.ceil(totalSize / (currentPage * currentSize)), totalSize);
    }

    public Pagination(Integer currentPage, Integer currentSize, Integer totalSize, T data) {
        this.current = new Page(currentPage, currentSize);
        this.total = new Page((int) Math.ceil(totalSize / (currentPage * currentSize)), totalSize);
        this.data = data;
    }

    public Page getTotal() {
        return total;
    }

    public void setTotal(Page total) {
        this.total = total;
    }

    public Page getCurrent() {
        return current;
    }

    public void setCurrent(Page current) {
        this.current = current;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public Pagination<T> clone() throws CloneNotSupportedException {
        Pagination<T> pagination = new Pagination<>();
        pagination.setCurrent(new Page(this.current.getPage(), this.current.getSize()));
        pagination.setTotal(new Page(this.total.getPage(), this.total.getSize()));
        pagination.setData(this.data);
        return pagination;
    }

    public static class Page {
        private Integer page;
        private Integer size;

        public Page() {
        }

        public Page(Integer page, Integer size) {
            this.page = page;
            this.size = size;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }
    }
}
