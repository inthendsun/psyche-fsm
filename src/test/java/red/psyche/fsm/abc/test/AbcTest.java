package red.psyche.fsm.abc.test;

import red.psyche.fsm.abc.*;
import red.psyche.fsm.base.BaseJunit4Test;
import red.psyche.fsm.FireResult;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class AbcTest extends BaseJunit4Test {
	
	@Resource(name="muteFsm")
	private AbcFsm abcFsm;

	@Test
	public void testMuteFsm() {
		AbcRecord record = new AbcRecord();
		record.setOrderId("2019062400001");
		record.setName("马云");
		record.setState(AbcState.INIT.name());

		AbcRecord execRecord = new AbcRecord();
		execRecord.setName("小马云");

		AbcFsmContext context = new AbcFsmContext(record,execRecord);

		FireResult result = abcFsm.fireWithResult(AbcEvent.CALL_TRANS, context);
		Assert.assertTrue(result.isSuccess());
	}
	
}
