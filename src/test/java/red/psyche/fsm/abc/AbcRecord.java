package red.psyche.fsm.abc;

import red.psyche.fsm.FsmRecord;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbcRecord implements FsmRecord {

	private String orderId;
    private String name;
    private String alias;
    private String state;

	@Override
	public String getRecId() {
		return this.orderId;
	}

	@Override
    public String getState() {
        return this.state;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" [");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (alias != null) {
			builder.append("alias=");
			builder.append(alias);
			builder.append(", ");
		}
		if (state != null) {
			builder.append("state=");
			builder.append(state);
		}
		builder.append("]");
		return builder.toString();
	}
}
