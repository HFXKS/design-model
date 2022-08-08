package com.xu.design.config;

import com.xu.design.common.constants.ConnectionTimeConstants;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedisTemplate<String, Object>
    redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new
                RedisTemplate<String, Object>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer
                (new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedissonClient getRedisson(){
        Config config = new Config();
        // 设置续期时间为10秒
//        config.setLockWatchdogTimeout(10000L);
        config.setUseScriptCache(true);
        SingleServerConfig singleConfig = config.useSingleServer();
        singleConfig.setAddress("redis://" + host + ":" + port).setPassword(password);
        // 设置对于master节点的连接池中连接数最大为100
        singleConfig.setConnectionPoolSize(ConnectionTimeConstants.CONNECTION_POOL_SIZE);
        // 如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，
        // 那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
        singleConfig.setIdleConnectionTimeout(ConnectionTimeConstants.MAX_CONNECTION_TIMEOUT);
        // 同任何节点建立连接时的等待超时。时间单位是毫秒。
        singleConfig.setConnectTimeout(ConnectionTimeConstants.MID_CONNECTION_TIMEOUT);
        // 等待节点回复命令的时间。该时间从命令发送成功时开始计时。
        singleConfig.setTimeout(ConnectionTimeConstants.MID_CONNECTION_TIMEOUT);
        return Redisson.create(config);
    }
}
