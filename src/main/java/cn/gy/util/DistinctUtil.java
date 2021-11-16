package cn.gy.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DistinctUtil {

    public static <T> List<Distinctable<T>> distinctList(List<Distinctable<T>> old) {
        return old.stream().map(Distinctable::distinct).collect(Collectors.toList());
    }

    public static <T> List<T> getDistinctList(List<T> list) {
        if (CollectionUtils.isEmpty(list))
            return new ArrayList<>();
        return list.stream().distinct().collect(Collectors.toList());
    }

}
