package com.dantefung.thinkinginspringbootsamples.expression;

import lombok.Builder;
import lombok.Data;

public class ApprovalConditionObject implements  ConditionObject<ApprovalConditionObject.ApprovalCondition>  {
    private ApprovalConditionObject.ApprovalCondition condition;

    public ApprovalConditionObject(ApprovalConditionObject.ApprovalCondition condition){
        this.condition=condition;
    }

    @Override
    public ApprovalConditionObject.ApprovalCondition getObject() {
        return condition;
    }

    @Override
    public Class<?> getObjectType() {
        return ApprovalConditionObject.ApprovalCondition.class;
    }
    @Builder
    @Data
    public static class ApprovalCondition{
        //级别
        private String level;
        //请假类型
        private Integer requestType;
        //班次
        private Integer shiftCount;
    }
}
