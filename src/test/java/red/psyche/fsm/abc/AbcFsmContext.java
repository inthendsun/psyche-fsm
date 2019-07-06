package red.psyche.fsm.abc;

import red.psyche.fsm.AbstractFsmContext;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class AbcFsmContext extends AbstractFsmContext<AbcRecord> {

    public AbcFsmContext(AbcRecord record, AbcRecord execRecord) {
        super(record, execRecord);
    }
}
