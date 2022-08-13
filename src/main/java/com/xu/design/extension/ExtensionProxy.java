package com.xu.design.extension;

import com.google.common.collect.Lists;
import com.xu.design.common.anno.ExtensionInject;
import com.xu.design.common.constants.CommonConstants;
import com.xu.design.utils.CommonClassUtils;
import com.xu.design.utils.CommonExceptionUtils;
import com.xu.design.utils.CommonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MethodInterceptor
 * 在调用目标对象的方法时，就可以实现在调用方法之前、调用方法过程中、调用方法之后对其进行控制。
 * 扩展点代理类
 */
@Slf4j
public class ExtensionProxy implements MethodInterceptor {



    private static final ExtensionProxy THIS = new ExtensionProxy();
    private static final Map<Class<?>, Object> PROXY_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, Map<String, Object>> EXTENSION_CACHE = new ConcurrentHashMap<>();

    /**
     * 创建代理增强器
     *
     * @return
     */
    public static Object createProxy(Class<?> clazz) {
        Object o = PROXY_CACHE.get(clazz);
        if (CommonUtils.isEmpty(o)) {
            o = Enhancer.create(clazz, THIS);
            PROXY_CACHE.put(clazz, o);
        }
        return o;
    }

    public static void cacheExtension(Object bean) {
        ExtensionInject abilityInject = bean.getClass().getAnnotation(ExtensionInject.class);
        if (CommonUtils.isEmpty(abilityInject)) {
            return;
        }
        String extensionCode = getExtensionCode(bean);
        if (CommonUtils.isNotEmpty(extensionCode)) {
            Map<String, Object> abilityMap = EXTENSION_CACHE.get(extensionCode);
            if (CommonUtils.isEmpty(abilityMap)) {
                abilityMap = new ConcurrentHashMap<>();
                EXTENSION_CACHE.put(extensionCode, abilityMap);
            }

            String abilityCode = abilityInject.value();
            if (abilityMap.containsKey(abilityCode)) {
                throw new RuntimeException("extension, duplicate abilityCode!");
            }
            if (!abilityCode.endsWith(CommonConstants.ABILITY)) {
                throw new RuntimeException("extension, abilityCode must ends with _ABILITY!");
            }
            abilityMap.put(abilityCode, bean);
        }
    }

    private static String getExtensionCode(Object bean) {
        for (Class<?> extensionType : CommonClassUtils.getDeclaredClass(bean).getInterfaces()) {
            ExtensionInject interfaceInject = extensionType.getAnnotation(ExtensionInject.class);
            if (CommonUtils.isNotEmpty(interfaceInject)) {
                String extensionCode = interfaceInject.value();
                if (!extensionCode.endsWith(CommonConstants.EXTENSION)) {
                    throw new RuntimeException("extension, extensionCode must ends with _EXTENSION!");
                }
                return extensionCode;
            }
        }
        return null;
    }
    
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // 获取扩展点 code
        Class<?> extensionType = getDeclaredClass(o);
        ExtensionInject interfaceInject = extensionType.getAnnotation(ExtensionInject.class);
        CommonExceptionUtils.checkBizEmpty(interfaceInject, "extension, can't find @ExtensionInject from extension interface! interface" + extensionType);
        String extensionCode = interfaceInject.value();

        Map<String, Object> abilityMap = EXTENSION_CACHE.get(extensionCode);
        CommonExceptionUtils.checkEmpty(abilityMap, "extension, ability is empty! interface:" + extensionType);

        // 查询扩展点上下文
        ExtContext context = getContext(args);
        CommonExceptionUtils.checkEmpty(context, "extension, proxy need ExtensionContext! interface:" + extensionType);

        //为了每个扩展点的上下文不互相污染，这边对上下文进行 deep copy
        BeanUtils.copyProperties(context, context);
        context.addStack();

        try {
            // 策略选择器 todo 这里需要一次重构来进行选择器的抽象
            List<Object> abilities = Lists.newArrayList();
            Map<Object, ExtStrategy> strategyCache = new HashMap<>();
            if (context.isChain()) {
                abilities.addAll(abilityMap.values());
                abilities.sort(Comparator.comparingInt(x -> x.getClass().getAnnotation(ExtensionInject.class).order()));
            } else {
                context.routeStrategy(extensionCode).forEach(x -> {
                    Object ability = abilityMap.get(x.getAbility());
                    strategyCache.put(ability, x);
                    abilities.add(ability);
                });
            }

            // 按策略执行扩展点
            for (Object ability : abilities) {
                // 缓存当前策略
                ExtStrategy currentStrategy = strategyCache.get(ability);
                CommonExceptionUtils.checkEmpty(ability, "extension, can't find ability! abilityCodes:" + currentStrategy);
                context.setCurrentStrategy(currentStrategy);
                // 执行当前能力
                context.addReturnStack(proxy.invoke(ability, args));
                if (context.isBraked()) {
                    log.info("extension, execute is braked, class:" + CommonClassUtils.getDeclaredClassName(ability));
                    break;
                }
            }
            return context.peekReturnStack();
        } finally {
            ExtContext.popStack();
        }
    }

    @SneakyThrows
    public static Class<?> getDeclaredClass(Object bean) {
        Class<?> clazz = bean.getClass();
        String className = clazz.getName();
        int dollarIndex = className.indexOf("$");
        if (dollarIndex > 0) {
            className = className.substring(0, dollarIndex);
            clazz = Class.forName(className);
        }
        return clazz;
    }

    /**
     * 获取扩展点上下文
     * 优先级为 inputContext > threadLocal > inputIContext
     *
     * @param objects
     * @return
     */
    private ExtContext getContext(Object[] objects) {
        IExtContext iContext = null;
        for (Object object : objects) {
            // 如果入参有 context, 则直接使用
            if (object instanceof ExtContext) {
                return (ExtContext) object;
            }
            // 如果入参有 IExtContext, 先暂存
            if (CommonUtils.isEmpty(iContext) && object instanceof IExtContext) {
                iContext = (IExtContext) object;
            }
        }
        ExtContext context = ExtContext.peekStack();
        if (CommonUtils.isEmpty(context) && CommonUtils.isNotEmpty(iContext)) {
            // 入参和线程 context 都无效，再使用 iContext 生产 context
            context = iContext.buildContext();
        }
        return context;
    }
}
