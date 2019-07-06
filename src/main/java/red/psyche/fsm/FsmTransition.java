package red.psyche.fsm;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author inthendsun
 */
@Data
public class FsmTransition implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = -3278778917307571780L;
    private String currState;
    private String eventName;
    private String nextState;
	private String actionBean;
	private String interceptBean;


	public FsmTransition(String currState, String eventName, String nextState, String actionBean) {
		super();
		this.currState = currState;
		this.eventName = eventName;
		this.nextState = nextState;
		this.actionBean = actionBean;
	}

	public FsmTransition() {
		super();
	}

	public String getKey() {
		return this.currState + "/" + this.eventName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(").append(currState).append("/").append(eventName).append("/")
				.append(nextState).append("/").append(actionBean).append("@").append(interceptBean).append(")");
		return builder.toString();
	}
}