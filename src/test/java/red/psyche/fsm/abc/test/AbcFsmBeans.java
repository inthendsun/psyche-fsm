package red.psyche.fsm.abc.test;

import red.psyche.fsm.abc.AbcEvent;
import red.psyche.fsm.abc.AbcFsm;
import red.psyche.fsm.abc.AbcState;
import red.psyche.fsm.FsmDefiner;
import red.psyche.fsm.listener.FsmLogPersistListener;
import red.psyche.fsm.spring.SpringFsmDefiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author inthendsun created on 2019-6-25
 */
@Configuration
public class AbcFsmBeans {

    @Autowired
    private AbcFsmLogPersister logPersister;

    @Bean
    public FsmLogPersistListener fsmLogPersistListener() {
        FsmLogPersistListener listener = new FsmLogPersistListener();
        listener.setPersister(logPersister);
        return listener;
    }

    @Bean
    public FsmDefiner muteFsmDefiner() {
        String[] textTransitions = new String[]{
                "INIT/CALL_TRANS/CALL/abcCallTransAction@init2CallInterceptor",
                "CALL/TRANS_PROC/PROC/abcTransProcAction",
                "PROC/TRANS_SUCC/SUCC/abcTransSuccAction",
                "PROC/TRANS_FAIL/FAIL/abcTransFailAction",
                "CALL/TRANS_SUCC/SUCC/abcTransSuccAction",
                "CALL/TRANS_FAIL/FAIL/abcTransFailAction",
        };
        FsmDefiner definer = new SpringFsmDefiner("muteFsm",textTransitions, AbcState.class, AbcEvent.class);
        return definer;
    }

    @Bean
    public AbcFsm muteFsm() {
        AbcFsm fsm = new AbcFsm(muteFsmDefiner());
        fsm.getListenerChain().addListener(fsmLogPersistListener());
        return fsm;
    }
}
