package cn.gy.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JDChen on 2019/08/26
 */

@ApiModel("客户首页数据")
public class CustomerHomePageVo {
    @ApiModelProperty("审核情况")
    private AuditSituation auditSituation;

    @ApiModelProperty("调用概览")
    private List<CallOverview> callOverviewList;

    @ApiModelProperty("图片概览")
    private ImageOverview imageOverview;

    public CustomerHomePageVo() {
        this.callOverviewList = new ArrayList<>();
    }

    public AuditSituation createAuditSituation(){
        AuditSituation auditSituation = new AuditSituation();
        this.setAuditSituation(auditSituation);
        return auditSituation;
    }

    public CallOverview createCallOverview(){
        CallOverview callOverview = new CallOverview();
        callOverviewList.add(callOverview);
        return callOverview;
    }

    public ImageOverview createImageOverview(){
        ImageOverview imageOverview = new ImageOverview();
        this.setImageOverview(imageOverview);
        return imageOverview;
    }

    public AuditSituation getAuditSituation() {
        return auditSituation;
    }

    public void setAuditSituation(AuditSituation auditSituation) {
        this.auditSituation = auditSituation;
    }

    public List<CallOverview> getCallOverviewList() {
        return callOverviewList;
    }

    public void setCallOverviewList(List<CallOverview> callOverviewList) {
        this.callOverviewList = callOverviewList;
    }

    public ImageOverview getImageOverview() {
        return imageOverview;
    }

    public void setImageOverview(ImageOverview imageOverview) {
        this.imageOverview = imageOverview;
    }

    @Override
    public String toString() {
        return "CustomerHomePageVo{" +
                "auditSituation=" + auditSituation +
                ", callOverviewList=" + callOverviewList +
                ", imageOverview=" + imageOverview +
                '}';
    }

    public static class AuditSituation {
        @ApiModelProperty("正在审核")
        private Integer underReview;
        @ApiModelProperty("待审核")
        private Integer pendingReview;

        public Integer getUnderReview() {
            return underReview;
        }

        public void setUnderReview(Integer underReview) {
            this.underReview = underReview;
        }

        public Integer getPendingReview() {
            return pendingReview;
        }

        public void setPendingReview(Integer pendingReview) {
            this.pendingReview = pendingReview;
        }

        @Override
        public String toString() {
            return "AuditSituation{" +
                    "underReview=" + underReview +
                    ", pendingReview=" + pendingReview +
                    '}';
        }

    }

    public static class CallOverview{
        @ApiModelProperty("API")
        private String api;
        @ApiModelProperty("调用量")
        private Integer totalNum;
        @ApiModelProperty("调用失败")
        private Integer failedNum;

        public String getApi() {
            return api;
        }

        public void setApi(String api) {
            this.api = api;
        }

        public Integer getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(Integer totalNum) {
            this.totalNum = totalNum;
        }

        public Integer getFailedNum() {
            return failedNum;
        }

        public void setFailedNum(Integer failedNum) {
            this.failedNum = failedNum;
        }

        @Override
        public String toString() {
            return "CallOverview{" +
                    "api='" + api + '\'' +
                    ", totalNum=" + totalNum +
                    ", failedNum=" + failedNum +
                    '}';
        }
    }

    public static class ImageOverview{
        @ApiModelProperty("违法图片")
        private Integer illegalNum;
        @ApiModelProperty("疑似图片")
        private Integer suspectedNum;
        @ApiModelProperty("正常图片")
        private Integer normalNum;


        public Integer getIllegalNum() {
            return illegalNum;
        }

        public void setIllegalNum(Integer illegalNum) {
            this.illegalNum = illegalNum;
        }

        public Integer getSuspectedNum() {
            return suspectedNum;
        }

        public void setSuspectedNum(Integer suspectedNum) {
            this.suspectedNum = suspectedNum;
        }

        public Integer getNormalNum() {
            return normalNum;
        }

        public void setNormalNum(Integer normalNum) {
            this.normalNum = normalNum;
        }

        @Override
        public String toString() {
            return "ImageOverview{" +
                    "illegalNum=" + illegalNum +
                    ", suspectedNum=" + suspectedNum +
                    ", normalNum=" + normalNum +
                    '}';
        }
    }

}
