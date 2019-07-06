package red.psyche.fsm.abc.action;

import red.psyche.fsm.abc.AbcEvent;
import red.psyche.fsm.abc.AbcFsmContext;
import red.psyche.fsm.AbstractFsmAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author inthendsun on 2019-5-26.
 */
@Slf4j
@Component
public class AbcTransProcAction extends AbstractFsmAction<AbcEvent,AbcFsmContext> {

    @Override
    public AbcEvent execute(AbcFsmContext context) {
        log.info("成功通知用户：交易正在处理，预计三分钟完成交易");
        return null;
    }
}
