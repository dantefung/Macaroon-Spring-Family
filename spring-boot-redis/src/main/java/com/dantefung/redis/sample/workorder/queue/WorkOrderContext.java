package com.dantefung.redis.sample.workorder.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderContext {
    /**
     * 是否测试人员专用
     */
    private boolean isTest;
    /**
     * 工单号
     */
    private WorkOrder worOrder;
    /**
     * 队列类型
     */
    private QueueType queueType;

    /**
     * 创建-正式队列（立即需要被派单
     * @return
     */
    public static WorkOrderContext buildImmediate() {
        return WorkOrderContext.builder().queueType(QueueType.immediate).build();
    }

    /**
     * 创建-挂起队列（挂起n小时执行）
     * @return
     */
    public static WorkOrderContext buildSuspended() {
        return WorkOrderContext.builder().queueType(QueueType.suspended).build();
    }

    /**
     * 转存队列（转存n天后执行）
     * @return
     */
    public static WorkOrderContext buildStored() {
        return WorkOrderContext.builder().queueType(QueueType.stored).build();
    }

    /**
     * 创建-正式队列（立即需要被派单）
     *
     * @param workCode
     * @param priority
     * @return
     */
    public static WorkOrderContext buildImmediate(String workCode, double priority) {
        WorkOrder workOrder = WorkOrder.builder().workCode(workCode).priority(priority).delayedTime(0).build();
        return WorkOrderContext.builder().worOrder(workOrder).queueType(QueueType.immediate).build();
    }

    /**
     * 创建-挂起队列（挂起n小时执行）
     *
     * @param workCode
     * @param priority
     * @param delayedTime
     * @return
     */
    public static WorkOrderContext buildSuspended(String workCode, double priority, long delayedTime) {
        WorkOrder workOrder = WorkOrder.builder().workCode(workCode).priority(priority).delayedTime(delayedTime).build();
        return WorkOrderContext.builder().worOrder(workOrder).queueType(QueueType.suspended).build();
    }

    /**
     * 转存队列（转存n天后执行）
     *
     * @param workCode
     * @param priority
     * @param delayedTime
     * @return
     */
    public static WorkOrderContext buildStored(String workCode, double priority, long delayedTime) {
        WorkOrder workOrder = WorkOrder.builder().workCode(workCode).priority(priority).delayedTime(delayedTime).build();
        return WorkOrderContext.builder().worOrder(workOrder).queueType(QueueType.stored).build();
    }

    /**
     * 队列类型
     */
    public enum QueueType {
        /**
         * 正式队列（立即需要被派单）
         */
        immediate,
        /**
         * 挂起队列（挂起n小时执行）
         */
        suspended,
        /**
         * 转存队列（转存n天后执行）
         */
        stored
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WorkOrder {
        /**
         * 工单号
         */
        private String workCode;
        /**
         * 优先级
         */
        private double priority;
        /**
         * 延迟时间
         */
        private long delayedTime;
    }
}