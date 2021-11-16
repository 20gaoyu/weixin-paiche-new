package cn.gy.extractor;

import cn.gy.constant.SysConstant;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @author: qingx
 * @create: 2021-04-08 14:21
 */
public class StringStringMapResultSetExtractor implements ResultSetExtractor<Map<String, String>> {
    private static final int LEGAL_COL_COUNT = 2;
    private static final int VALUE_COL_INDEX = 2;
    @Override
    public Map<String, String> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        int colCnt = resultSet.getMetaData().getColumnCount();
        if (colCnt < LEGAL_COL_COUNT) {
            throw new IncorrectResultSetColumnCountException(LEGAL_COL_COUNT, colCnt);
        }
        Map<String, String> map = new HashMap<>(SysConstant.INITIAL_MAP_CAPACITY);
        while (resultSet.next()){
            map.put(resultSet.getString(1), resultSet.getString(VALUE_COL_INDEX));
        }
        return map;
    }
}
