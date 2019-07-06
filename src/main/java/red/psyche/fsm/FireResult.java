package red.psyche.fsm;

import lombok.Getter;
import lombok.Setter;

/**
 * 触发结果
 * @author inthendsun
 *
 */
@Getter
@Setter
@SuppressWarnings("rawtypes")
public class FireResult {

	private boolean success;
	/*
	 * optional
	 */
	private String failedMemo;
	/*
	 * optional
	 */
	private StateTransition transition;
	
	public FireResult() {
		super();
	}
	
	public FireResult(boolean success, String failedMemo) {
		super();
		this.success = success;
		this.failedMemo = failedMemo;
	}

	public FireResult(boolean success, StateTransition transition) {
		super();
		this.success = success;
		this.transition = transition;
	}

	public FireResult(boolean success, String failedMemo, StateTransition transition) {
		super();
		this.success = success;
		this.failedMemo = failedMemo;
		this.transition = transition;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FireResult [success=").append(success).append(", failedMemo=").append(failedMemo)
				.append(", transition=").append(transition).append("]");
		return builder.toString();
	}
}
