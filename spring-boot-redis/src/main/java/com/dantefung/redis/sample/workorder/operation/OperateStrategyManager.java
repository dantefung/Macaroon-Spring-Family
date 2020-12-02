package com.dantefung.redis.sample.workorder.operation;

import com.dantefung.redis.utils.Result;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 该类的主要作用，就是对外暴露工单操作策略类的管理，外部无需关注策略类的存在，策略类实例的创建由该类负责。
 */
@Component
@Slf4j
public class OperateStrategyManager {

    static final Map<OperateContext.OperateStrategyEnum, AbstractOperateStrategy> OPERATE_STRATEGY_MAP = Maps.newHashMapWithExpectedSize(6);

    @Autowired
	private SubmitWithStoreOperateStrategy submitWithStoreOperateStrategy;
	@Autowired
	private SubmitWithSuspendOperateStrategy SubmitWithSuspendOperateStrategy;


    @PostConstruct
    private void init() {
        OPERATE_STRATEGY_MAP.put(OperateContext.OperateStrategyEnum.SUSPEND_WORK_ORDER,
				SubmitWithSuspendOperateStrategy);
		OPERATE_STRATEGY_MAP.put(OperateContext.OperateStrategyEnum.STORE_WORK_ORDER,
				submitWithStoreOperateStrategy);
    }

    /**
     * 对外提供对策略类的调用
     * @param context
     * @return
     */
    public Result<String> execute(OperateContext context) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("OperateStrategyManager.execute");
        AbstractOperateStrategy operateStrategy = OPERATE_STRATEGY_MAP.get(context.getOperateStrategyEnum());
        context.buildExecuteResultWithSuccess();
        operateStrategy.execute(context);
        Result<Boolean> executeResult = context.getExecuteResult();
        if(context.getExecuteResult().isSuccess()) {
            return Result.ok(executeResult.getMessage());
        }
        stopWatch.stop();
        long spendMillSeconds = stopWatch.getLastTaskTimeMillis();
        long duration = (System.currentTimeMillis() - spendMillSeconds) / 1000;
        String executeResultMsg = executeResult.getMessage();
        log.info("[execute] done,duration={},executeResultMsg={}",duration,executeResultMsg);
        return Result.fail(500, executeResultMsg);
    }
}