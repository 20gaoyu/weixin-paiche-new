package cn.gy.util;

import lombok.Data;

import java.util.Collections;
import java.util.List;

public class PagingUtil {

     public static <T> List<T>  getPagingList(List<T> list,Integer pageIndex,Integer pageSize){
        Paging paging = Paging.pagination(list.size(),pageSize,pageIndex);
        int fromIndex = paging.getQueryIndex();
        int toIndex = 0;
        if (fromIndex + paging.getPageSize() >= list.size()){
            toIndex = list.size();
        }else {
            toIndex = fromIndex +  paging.getPageSize();
        }
        if (fromIndex > toIndex){
            return Collections.EMPTY_LIST;
        }
        return list.subList(fromIndex,toIndex);
    }

    @Data
    public static class Paging {
        private Integer totalNum;//总条数
        private Integer totalPage;//总页数
        private Integer pageSize;//每页条数
        private Integer pageIndex;//当前页码
        private Integer queryIndex;//当前页从第几条开始查

        public static Paging pagination(Integer totalNum,Integer pageSize,Integer pageIndex){
            Paging page = new Paging();
            page.setTotalNum(totalNum);
            Integer totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
            page.setTotalPage(totalPage);
            Integer realIndex = pageIndex > 0?pageIndex:1;
            page.setPageIndex(realIndex);
            page.setPageSize(pageSize);
            page.setQueryIndex(pageSize * (realIndex - 1));
            return page;
        }

    }
}
