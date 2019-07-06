package red.psyche.fsm.spring;

import red.psyche.fsm.FsmDefiner;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author inthendsun
 */
@Getter
@Setter
@ToString
public class SpringFsmDefiner implements FsmDefiner {

    private String name;

    private String[] textTransitions;

    private Class<? extends Enum> stateEnum;

    private Class<? extends Enum> eventEnum;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * @param name            状态机名字
     * @param textTransitions 状态迁移
     * @param stateEnum       状态枚举类
     * @param eventEnum       事件枚举类
     */
    public SpringFsmDefiner(String name, String[] textTransitions, Class<? extends Enum> stateEnum, Class<? extends Enum> eventEnum) {
        this.name = name;
        this.textTransitions = textTransitions;
        this.stateEnum = stateEnum;
        this.eventEnum = eventEnum;
    }

    @Override
    public <T> T getBean(String actionName, Class<T> clazz) {
        return applicationContext.getBean(actionName, clazz);
    }
}