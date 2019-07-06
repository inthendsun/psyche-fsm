package red.psyche.fsm.interceptor;

import red.psyche.fsm.AbstractFsmContext;
import red.psyche.fsm.FsmTransition;

/**
 * 明确迁移拦截，区别于状态机的listener过于笼统
 * @author inthendsun created on 2019-6-25
 */
public interface Interceptor<C extends AbstractFsmContext> {
    /**
     * 迁移前拦截,在listener之后
     * @param transition
     * @param context
     */
    void beforeTransition(FsmTransition transition, C context);


    /**
     * action执行前
     * @param transition
     * @param context
     */
    void beforeExecute(FsmTransition transition, C context);

    /**
     * action execute后
     * @param transition
     * @param context
     * @param e
     */
    void afterExecute(FsmTransition transition, C context, RuntimeException e);
}
