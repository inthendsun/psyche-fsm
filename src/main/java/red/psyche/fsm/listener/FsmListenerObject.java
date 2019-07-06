package red.psyche.fsm.listener;

import red.psyche.fsm.AbstractFsmContext;
import red.psyche.fsm.FsmTransition;
import lombok.Data;

/**
 * @author inthendsun created on 2019-6-23
 */
@Data
public class FsmListenerObject {

    private String fsmName;

    private String currState;

    private String eventName;

    private FsmTransition transition;

    private AbstractFsmContext<?> context;

    private String message;

    private String recId;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("recId=").append(this.recId);
        sb.append(",fsmName=").append(fsmName);
        if(this.transition == null) {
            sb.append(", currState=").append(currState);
            sb.append(", eventName=").append(eventName);
        } else {
            sb.append(", transition=").append(transition);
        }
        sb.append(", context=").append(context);
        sb.append(", message=").append(message);
        sb.append("}");
        return sb.toString();
    }
}
