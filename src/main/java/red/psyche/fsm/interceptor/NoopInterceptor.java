package red.psyche.fsm.interceptor;

import red.psyche.fsm.AbstractFsmContext;
import red.psyche.fsm.FsmTransition;

/**
 * 什么都不拦截的interceptor，主要解决总是判断null问题
 * @author inthendsun created on 2019-6-26
 */
public class NoopInterceptor implements Interceptor{
    @Override
    public void beforeTransition(FsmTransition transition, AbstractFsmContext context) {
    }

    @Override
    public void beforeExecute(FsmTransition transition, AbstractFsmContext context) {
    }

    @Override
    public void afterExecute(FsmTransition transition, AbstractFsmContext context, RuntimeException e) {
    }
}
