package cn.gy.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DomainUtil {

    /**
     * 领域对象和可视对象互转
     *
     * @param originPojo
     * @param newClass
     * @param <N>
     * @param <O>
     * @return
     */
    public static <N, O> N transferTo(O originPojo, Class<N> newClass) {
        N newPojo;
        try {
            newPojo = newClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.warn("Domain transfer error",e);
            return null;
        }
        BeanUtils.copyProperties(originPojo, newPojo);
        return newPojo;
    }

    public static <N, O> List<N> transferTo(List<O> originList, Class<N> newClass) {
        List<N> newList = new ArrayList<>();
        originList.forEach(originPojo -> newList.add(transferTo(originPojo, newClass)));
        return newList;
    }

}
