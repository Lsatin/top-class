package com.lsatin.topclass.web.basic.pojo;

public final class Pagination<T> {

    private Page total;
    private Page current;
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
