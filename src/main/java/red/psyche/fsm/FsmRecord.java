package red.psyche.fsm;

/**
 * @author inthendsun
 */
public interface FsmRecord {
    /**
     * 获取状态
     * @return
     */
    String getState();

    /**
     * 设置状态
     * @param state
     */
    void setState(String state);

    /**
     * 获取recId，状态机广播事件时使用
     * @return 返回Record的唯一标识
     */
    String getRecId();
}
