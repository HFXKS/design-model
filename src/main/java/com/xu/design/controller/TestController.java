package com.xu.design.controller;

import com.xu.design.common.anno.Extension;
import com.xu.design.extension.ExtStrategy;
import com.xu.design.model.ActivityModel;
import com.xu.design.service.DesignUserService;
import com.xu.design.service.activityService.ActivityPermitExtension;
import com.xu.design.utils.CommonExceptionUtils;
import com.xu.design.utils.RedisUtils;
import com.xu.design.utils.ThreadPoolUtils;
import lombok.SneakyThrows;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/xu")
public class TestController {

    @Resource
    private DesignUserService designUserService;
    @Extension
    private ActivityPermitExtension activityPermitExtension;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private RedissonClient redisson;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/pushMessage")
    public Object get(@RequestParam("id") int id) {
        rocketMQTemplate.convertAndSend("first-topic","Hello World" + id);
        return "OK!";
    }

    @GetMapping("/testGet")
    public String testGet(){
        return "Test";
    }

    @GetMapping("/testException")
    public Object testException(){
        return 1 / 0;
    }

    @GetMapping("redisTest")
    public Object redisTest(){
        redisUtils.set("test", "redisTest");
        return redisUtils.get("test");
    }

    @GetMapping("/testThreadPool")
    public void testThreadPool(){
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.getThreadPool();
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(()->{
                System.out.println(Thread.currentThread().getName());
            });
        }
    }

    @SneakyThrows
    @GetMapping("redissonTest")
    public Object redissonTest(){
        RLock lock = redisson.getLock("redissonTest");
        //l:waitTime  l1:leaseTime
        boolean res = lock.tryLock(200, 12000, TimeUnit.MILLISECONDS);
        if (res) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }else {
            CommonExceptionUtils.throwException("repeat request!!!");
        }
        return "OK!";
    }

    @GetMapping("/queryDatabaseTest")
    public Object queryDatabaseTest(){
        return designUserService.selectAll();
    }

    /**
     * 注解式策略扩展测试
     */
    @GetMapping("/annoPolicyTest")
    public void AnnoPolicyTest() {
        ActivityModel activityModel = new ActivityModel();
        List<ExtStrategy> permitStrategies = new ArrayList<>();

        ExtStrategy extStrategy = new ExtStrategy();
        extStrategy.setExtension("ACTIVITY_PERMIT_EXTENSION");
        extStrategy.setAbility("ACTIVITY_CHANCE_PERMIT_ABILITY");

        ExtStrategy extStrategy1 = new ExtStrategy();
        extStrategy1.setExtension("ACTIVITY_PERMIT_EXTENSION");
        extStrategy1.setAbility("ACTIVITY_MEDAL_PERMIT_ABILITY");

        permitStrategies.add(extStrategy);
        permitStrategies.add(extStrategy1);
        activityModel.setPermitStrategies(permitStrategies);
        activityPermitExtension.isPermit(activityModel);
    }
}
