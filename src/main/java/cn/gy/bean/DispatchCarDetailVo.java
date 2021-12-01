package cn.gy.bean;

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
        return "DispatchCarDetailVo{" +
                "driverId=" + driverId +
                ", applicant='" + applicant + '\'' +
                ", user='" + user + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", useReason='" + useReason + '\'' +
                ", destination='" + destination + '\'' +
                ", status='" + status + '\'' +
                ", cancelReason='" + cancelReason + '\'' +
                "} " ;
    }
}
