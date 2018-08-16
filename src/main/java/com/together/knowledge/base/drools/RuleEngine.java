package com.together.knowledge.base.drools;

public interface RuleEngine {
    /**
     * 初始化规则引擎
     */
    public void initEngine() throws Exception;

    /**
     * 刷新规则引擎中的规则
     */
    public void refreshEnginRule() throws Exception;

    /**
     * 执行规则引擎
     * @param obj
     */
    public void executeRuleEngine(final Object obj) throws Exception;

    /**
     * 测试规则文件是否有问题  如果有问题抛出异常，如果无问题正常返回
     * @param obj
     * @throws Exception
     */
    public void testRuleEngine(final Object obj) throws Exception;
}
