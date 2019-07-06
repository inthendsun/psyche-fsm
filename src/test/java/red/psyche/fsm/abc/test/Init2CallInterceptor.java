package red.psyche.fsm.abc.test;

import red.psyche.fsm.abc.AbcFsmContext;
import red.psyche.fsm.FsmTransition;
import red.psyche.fsm.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 从INIT迁移到CALL状态拦截器
 * @author inthendsun created on 2019-6-25
 */
@Component
public class Init2CallInterceptor implements Interceptor<AbcFsmContext> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void beforeTransition(FsmTransition transition, AbcFsmContext context) {
        logger.info("明确迁移拦截beforeTransit");
    }

    @Override
    public void beforeExecute(FsmTransition transition, AbcFsmContext context) {
        logger.info("明确迁移拦截beforeExecute");
    }

    @Override
    public void afterExecute(FsmTransition transition, AbcFsmContext context, RuntimeException e) {
        if(e != null) {
            logger.error("明确迁移拦截afterTransit", e);
        } else {
            logger.info("明确迁移拦截afterTransit");
        }

    }
}
