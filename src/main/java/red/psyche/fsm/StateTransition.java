package red.psyche.fsm;


import red.psyche.fsm.interceptor.Interceptor;
import lombok.Data;

/**
 *
 * @author inthendsun
 * @param <T>
 */
@Data
@SuppressWarnings("rawtypes")
public class StateTransition<T extends AbstractFsmAction> {
	
	String currentState;
	String nextState;
	String event;
	T action;
	private FsmTransition transition;
	private Interceptor interceptor;

	public StateTransition(String currentState, String nextState, String event, T action, FsmTransition transition) {
		super();
		this.currentState = currentState;
		this.nextState = nextState;
		this.event = event;
		this.action = action;
		this.transition = transition;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(").append(currentState).append("/").append(event).append("/")
				.append(nextState).append("/").append(action==null?null:action.getClass().getSimpleName()).append(")");
		return builder.toString();
	}
	
}
