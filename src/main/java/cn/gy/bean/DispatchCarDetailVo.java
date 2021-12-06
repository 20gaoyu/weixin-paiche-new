package cn.gy.bean;

import cn.gy.util.DateTimeUtil;
import lombok.Data;

/**
 * @Author : gaoyu
 * @CreateTime: 2021/11/19 14:55
 */
@Data
public class DispatchCarDetailVo extends DispatchCarDetail {
    private Long driverId;

    @Override
    public String toString() {
        cancelReason=cancelReason==null?"无": cancelReason;
        return
                "工单id : " + driverId + ";\n"+
                "申请人:" + applicant + ";\n"+
                "申请人电话:" + telephone + ";\n"+
                "申请人部门:" + departmentName + ";\n"+
                "使用人:" + user + ";\n"+
                "开始时间:" + DateTimeUtil.getDateString(startTime) +";\n"+
                "结束时间:" +DateTimeUtil.getDateString(endTime) + ";\n"+
                "用车原因:" + useReason + ";\n"+
                "出行目的地:" + destination + ";\n"+
                "审核状态:" + status + ";\n"+
                "驳回原因:" + cancelReason+ ";\n";
    }
}
