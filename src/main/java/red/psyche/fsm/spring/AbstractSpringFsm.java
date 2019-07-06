package red.psyche.fsm.spring;

import red.psyche.fsm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.Setter;

/**
 * @author inthendsun created on 2019-6-23
 */
@Setter
@SuppressWarnings({"unchecked","rawtypes"})
public abstract class AbstractSpringFsm<S, E, R extends FsmRecord, C extends AbstractFsmContext<R>> extends AbstractFsm {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransactionTemplate transactionTemplate;

    public AbstractSpringFsm(FsmDefiner definer) {
        super(definer);
    }

    @Override
    public boolean transactionUpdateState(final AbstractFsmContext context, final StateTransition stateTransition) {
        final String fsmName = super.getName();
        Boolean updateResult = transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                boolean updateSucc = false;
                try {
                    updateSucc = updateFSMState(stateTransition, (C) context);
                    if (updateSucc) {
                        updateSucc = stateTransition.getAction().txChangeState(context);
                    }
                } catch (Exception e) {
                    log.error(fsmName + " update database state error", e);
                    status.setRollbackOnly();
                    updateSucc = false;
                } finally {
                    if (!updateSucc) {
                        status.setRollbackOnly();
                    }
                }
                return updateSucc;
            }
        });
        if (!updateResult) {
            log.info("{} update state failure",fsmName);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 更新状态
     * @param stateTransition
     * @param context
     * @return
     */
    protected abstract boolean updateFSMState(StateTransition stateTransition, C context);

}
