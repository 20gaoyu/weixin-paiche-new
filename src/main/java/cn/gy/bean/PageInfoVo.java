package cn.gy.bean;

import java.util.List;
import java.util.Objects;

public class PageInfoVo<T> {

    private List<T> list;

    private long total;

    public PageInfoVo(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }

    public PageInfoVo() {
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageInfoVo<?> that = (PageInfoVo<?>) o;
        return total == that.total &&
                Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, total);
    }

    @Override
    public String toString() {
        return "PageInfoVo{" +
                "list=" + list +
                ", total=" + total +
                '}';
    }
}
