package red.psyche.fsm;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author inthendsun
 * @param <R>
 */
@Data
public abstract class AbstractFsmContext<R> {

    private R record;

    private R execRecord;

    public AbstractFsmContext(R record, R execRecord) {
        this.record = record;
        this.execRecord = execRecord;
    }

    private R append;

    /**
     * 上下文context
     */
    private Map<String,Object> context = new HashMap<String,Object>();

    /**
     * 添加上下文
     * @param key
     * @param value
     */
    public void addContext(String key,Object value) {
        context.put(key,value);
    };

    /**
     * 移除上下文
     * @param key
     */
    public void removeContext(String key) {
        context.remove(key);
    }

    /**
     * 获取上下文
     * @param key
     */
    public Object getContext(String key) {
        return context.remove(key);
    }



}
