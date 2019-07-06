package red.psyche.fsm.abc.action;

import red.psyche.fsm.abc.AbcEvent;
import red.psyche.fsm.abc.AbcFsmContext;
import red.psyche.fsm.AbstractFsmAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AbcCallTransAction extends AbstractFsmAction<AbcEvent,AbcFsmContext> {
    @Override
    public AbcEvent execute(AbcFsmContext context) {
        log.info("交易调用结果成功");
        return AbcEvent.TRANS_SUCC;
    }
}
