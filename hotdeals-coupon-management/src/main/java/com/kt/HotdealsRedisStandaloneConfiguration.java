package com.kt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@SuppressWarnings("all")
public class HotdealsRedisStandaloneConfiguration {

	/**
	 * Redis Cluster Config
	 */
	@Autowired
	private HotdealsRedisStandaloneProperties hotdealsRedisStandaloneProperties;

	@Bean(destroyMethod = "destroy")
	@Primary
	public JedisConnectionFactory standaloneJedisConnectionFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMinIdle(hotdealsRedisStandaloneProperties.getJedis().getPool().getMinIdle());
		poolConfig.setMaxIdle(hotdealsRedisStandaloneProperties.getJedis().getPool().getMaxIdle());
		poolConfig.setMaxTotal(hotdealsRedisStandaloneProperties.getJedis().getPool().getMaxActive());

		JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
		factory.setHostName(hotdealsRedisStandaloneProperties.getHost());
		factory.setPort(hotdealsRedisStandaloneProperties.getPort());
		return factory;
	}

	@Bean
	@Primary
	public RedisTemplate<Object, Object> standaloneRedisTemplate() {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(standaloneJedisConnectionFactory());
		return template;
	}

	@Bean
	@Primary
	public StringRedisTemplate standaloneStringRedisTemplate() {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(standaloneJedisConnectionFactory());
		return redisTemplate;
	}

}
