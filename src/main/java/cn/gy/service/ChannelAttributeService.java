package cn.gy.service;


import cn.gy.constant.SysConstant;
import cn.gy.dto.ChannelAttribute;
import cn.gy.dto.ChannelUpdateParam;
import cn.gy.exception.RequestParamException;
import cn.gy.util.JacksonUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenws
 * @version 1.0
 * @date 2021/3/10
 */
@Service
public class ChannelAttributeService {
    public static final AtomicLong CHANGE_TIME = new AtomicLong(0);
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelAttributeService.class);
    private static final String CHANNEL = "channel";
    private static final String VT = "vt";
    private static final String PATH_INDEX = "path_index";
    private static final String PIN = "pin";
    private static final String DOMAIN_TYPE = "domain_type";
    private static final String EQUALS = "=";
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Value("${channel.attribute.table}")
    private String channelAttributeTable;



    public String updateAndInsert(ChannelUpdateParam channelUpdateParam) throws Exception {
        List<String> channelList = jdbcTemplate.queryForList(String.format("SELECT channel FROM %s",channelAttributeTable),String.class);
        List<ChannelAttribute> chanelAttributeParams = channelUpdateParam.getChannelAttributes();
        List<String> sqlList = new ArrayList<>();
        for(ChannelAttribute channelAttribute :chanelAttributeParams){
            if(channelAttributeIsNull(channelAttribute)) {
                throw new Exception(" param about 'vt,path_index,pin,domain_type'  cannot be all null,one is not null at least,please check!");
            }
            String channel = channelAttribute.getChannel();
            if(channelList.contains(channel)){
                String updateSql = String.format("UPDATE %s SET %s WHERE channel = '%s'",channelAttributeTable,
                        addUpdateValue(channelAttribute),channelAttribute.getChannel());
                LOGGER.info("updateSql:{}",updateSql);
                sqlList.add(updateSql);
            }else{
                String insertSql = getInsertSql(channelAttribute);
                LOGGER.info("insertSql:{}",insertSql);
                sqlList.add(insertSql);
            }
        }
        String[] updateInsertSql = new String[sqlList.size()];
        jdbcTemplate.batchUpdate(sqlList.toArray(updateInsertSql));
        CHANGE_TIME.set(System.currentTimeMillis()/1000);
        return  String.format("Successfully updated count:%s", sqlList.size());
    }

    public ObjectNode queryChannelAttribute(String channel) throws RequestParamException {
        String querySql = String.format("SELECT channel,vt,path_index,pin,domain_type FROM %s WHERE channel = '%s'",channelAttributeTable,channel);
        List<ChannelAttribute> query;
        try{
            query = jdbcTemplate.query(querySql, new RowMapper<ChannelAttribute>() {
                @Override
                public ChannelAttribute mapRow(ResultSet arg0, int arg1) throws SQLException {
                    ChannelAttribute channelAttribute = new ChannelAttribute();
                    channelAttribute.setChannel(channel);
                    channelAttribute.setVt((Integer) arg0.getObject(VT));
                    channelAttribute.setPathIndex((Integer) arg0.getObject(PATH_INDEX));
                    channelAttribute.setPin(arg0.getString(PIN));
                    channelAttribute.setDomainType(arg0.getString(DOMAIN_TYPE));
                    return channelAttribute;
                }
            });
        }catch(Exception e){
            throw new RequestParamException("query sql exception:"+e);
        }
        if(query.isEmpty()){
            LOGGER.info("not exist channel : {} ", channel);
            return JacksonUtil.createObjectNode();
        }else{
            return getQueryObjectNode(query.get(0));
        }
    }

    public void deleteChannel(String  channel){
        String deleteSql = String.format("DELETE FROM %s WHERE channel = '%s'",channelAttributeTable,channel);
        LOGGER.info("execute channelAttribute delete sql:{}",deleteSql);
        jdbcTemplate.execute(deleteSql);
        CHANGE_TIME.set(System.currentTimeMillis()/1000);
    }

    public String addUpdateValue(ChannelAttribute param){
        StringJoiner stringJoiner = new StringJoiner(SysConstant.COMMA, StringUtils.SPACE, StringUtils.SPACE);
        addSingleUpdateValue(stringJoiner,param.getVt(),VT);
        addSingleUpdateValue(stringJoiner,param.getPathIndex(),PATH_INDEX);
        addSingleUpdateValue(stringJoiner,param.getPin(),PIN);
        addSingleUpdateValue(stringJoiner,param.getDomainType(),DOMAIN_TYPE);
        return stringJoiner.toString();
    }

    public String getInsertSql(ChannelAttribute channelAttribute){
        String pinValue = addSingleQuotes(channelAttribute.getPin());
        String domainTypeValue = addSingleQuotes(channelAttribute.getDomainType());
        return String.format("INSERT INTO `%s` (`channel`, `vt`, `path_index`, `pin`, `domain_type`) VALUES ('%s', %s, %s,%s,%s)",
                channelAttributeTable,channelAttribute.getChannel(),channelAttribute.getVt(),
                channelAttribute.getPathIndex(), pinValue, domainTypeValue);
    }

    public void addSingleUpdateValue(StringJoiner stringJoiner,Object object,String fieldName){
        if(object != null){
            if(object instanceof String) {
                object = SysConstant.SINGLE_QUOTES+object+SysConstant.SINGLE_QUOTES;
            }
            stringJoiner.add(StringUtils.join(fieldName, EQUALS, object));
        }
    }

    public String addSingleQuotes(String str){
         return str == null ? null :SysConstant.SINGLE_QUOTES+str+SysConstant.SINGLE_QUOTES;
    }

    private ObjectNode getQueryObjectNode(ChannelAttribute channelAttribute) {
        ObjectNode objectNode = JacksonUtil.createObjectNode();
        objectNode.put(CHANNEL, channelAttribute.getChannel());
        objectNode.put(VT, channelAttribute.getVt());
        objectNode.put(PATH_INDEX, channelAttribute.getPathIndex());
        objectNode.put(PIN,channelAttribute.getPin());
        objectNode.put(DOMAIN_TYPE,channelAttribute.getDomainType());
        return objectNode;
    }

    public Boolean channelAttributeIsNull(ChannelAttribute channelAttribute){
        Boolean allNull = channelAttribute.getVt()== null&& channelAttribute.getPathIndex() == null
                &&channelAttribute.getPin() == null && channelAttribute.getDomainType() == null;
        return Boolean.TRUE.equals(allNull) ? Boolean.TRUE :Boolean.FALSE;
    }

}
