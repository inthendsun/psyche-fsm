package red.psyche.fsm;

/**
 * @author inthendsun
 */
@SuppressWarnings("rawtypes")
public class NoopAction extends AbstractFsmAction {

	@Override
	public Object execute(AbstractFsmContext context) {
		return null;
	}
}
