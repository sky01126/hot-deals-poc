package com.kt;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.google.common.collect.Maps;
import com.kthcorp.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
public class HotdealsRedisClusterConfiguration {

	private static final String REDIS_MAP_PROPERTY_SOURCE = "RedisClusterConfiguration";

	private static final String REDIS_CLUSTER_NODES_CONFIG_PROPERTY = "spring.redis.cluster.nodes";

	private static final String REDIS_CLUSTER_MAX_REDIRECTS_CONFIG_PROPERTY = "spring.redis.cluster.max-redirects";

	/**
	 * Redis Cluster Config
	 */
	@Autowired
	private HotdealsRedisClusterProperties hotdealsRedisClusterProperties;

	@Bean(destroyMethod = "destroy")
	public JedisConnectionFactory clusterJedisConnectionFactory() {
		Map<String, Object> source = Maps.newHashMap();
		source.put(REDIS_CLUSTER_NODES_CONFIG_PROPERTY,
				StringUtils.join(hotdealsRedisClusterProperties.getCluster().getNodes(), ","));
		source.put(REDIS_CLUSTER_MAX_REDIRECTS_CONFIG_PROPERTY,
				hotdealsRedisClusterProperties.getCluster().getMaxRedirects());
		log.debug("Source : {}", source.toString());

		RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(
				new MapPropertySource(REDIS_MAP_PROPERTY_SOURCE, source));

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMinIdle(hotdealsRedisClusterProperties.getJedis().getPool().getMinIdle());
		poolConfig.setMaxIdle(hotdealsRedisClusterProperties.getJedis().getPool().getMaxIdle());
		poolConfig.setMaxTotal(hotdealsRedisClusterProperties.getJedis().getPool().getMaxActive());

		return new JedisConnectionFactory(clusterConfiguration, poolConfig);
	}

	@Bean("clusterRedisTemplate")
	public RedisTemplate<Object, Object> clusterRedisTemplate() {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(clusterJedisConnectionFactory());
		return template;
	}

	@Bean("clusterStringRedisTemplate")
	public StringRedisTemplate clusterStringRedisTemplate() {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(clusterJedisConnectionFactory());
		return redisTemplate;
	}

}
