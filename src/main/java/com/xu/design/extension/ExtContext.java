package com.xu.design.extension;

import com.google.common.collect.Lists;
import com.xu.design.utils.CommonUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 扩展点上下文
 * 基础
 */
@Data
@Accessors(chain = true)
public class ExtContext implements IExtContext {
    /**
     * 上下文线程缓存
     */
    private static final ThreadLocal<Stack<ExtContext>> STACK = new ThreadLocal<>();

    /**
     * 扩展点策略
     */
    private Map<String, List<ExtStrategy>> strategy = new HashMap<>();

    /**
     * 当前策略
     */
    private ExtStrategy currentStrategy;

    /**
     * 扩展点中断执行标志
     */
    private boolean braked = false;

    /**
     * 是否责任链模式
     */
    private boolean isChain = false;

    /**
     * 出参栈
     */
    public Stack<Object> returnStack = new Stack<>();


    /**
     * 上下文压栈
     *
     * @param context
     */
    private static void addStack(ExtContext context) {
        Stack<ExtContext> stack = STACK.get();
        if (CommonUtils.isEmpty(stack)) {
            stack = new Stack<>();
            STACK.set(stack);
        }
        stack.add(context);
    }

    /**
     * 上下文出栈
     *
     * @return
     */
    public static ExtContext popStack() {
        Stack<ExtContext> stack = STACK.get();
        if (CommonUtils.isNotEmpty(stack)) {
            return stack.pop();
        }
        return null;
    }

    public static ExtContext peekStack() {
        Stack<ExtContext> stack = STACK.get();
        if (CommonUtils.isNotEmpty(stack)) {
            return stack.peek();
        }
        return null;
    }

    public void addStack() {
        ExtContext.addStack(this);
    }


    public void addReturnStack(Object o) {
        returnStack.add(o);
    }

    public Object peekReturnStack() {
        if (CommonUtils.isNotEmpty(returnStack)) {
            return this.returnStack.peek();
        }
        return null;
    }

    /**
     * 标记流程中断
     */
    public static void markBreak() {
        peekStack().setBraked(true);
    }

    @Override
    public ExtContext buildContext() {
        return this;
    }

    /**
     * 按 code 路由扩展点策略
     *
     * @param extensionCode
     * @return
     */
    public List<ExtStrategy> routeStrategy(String extensionCode) {
        return Optional.ofNullable(strategy.get(extensionCode)).orElse(Lists.newArrayList());
    }

    /**
     * 不完美
     *
     * @return
     */
    public static ExtContext newContext() {
        return new ExtContext();
    }

    public static ExtContext newChainContext() {
        return new ExtContext().setChain(true);
    }

    public static ExtContext newContext(Map<String, List<ExtStrategy>> strategy) {
        ExtContext context = newContext();
        if (CommonUtils.isNotEmpty(strategy)) {
            context.setStrategy(strategy);
        }
        return context;
    }

    public static ExtContext newContext(List<ExtStrategy> extStrategies) {
        Map<String, List<ExtStrategy>> strategy = extStrategies.stream().collect(Collectors.groupingBy(ExtStrategy::getExtension));
        return newContext(strategy);
    }
}
