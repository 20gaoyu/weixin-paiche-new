package cn.gy.util;

import org.apache.commons.lang3.RandomUtils;
import tk.mybatis.mapper.genid.GenId;

public class GenIntId implements GenId<Integer> {
    @Override
    public Integer genId(String table, String column) {
        return RandomUtils.nextInt();
    }
}
