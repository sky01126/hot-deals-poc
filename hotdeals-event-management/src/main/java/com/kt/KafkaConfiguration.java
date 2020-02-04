package com.kt;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

import com.kt.commons.config.Constants;

/**
 * Kafka 연결 Configuration
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
@Configuration
public class KafkaConfiguration {

	@Bean
	public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory, KafkaTemplate<Object, Object> template) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		configurer.configure(factory, kafkaConsumerFactory);

		// dead-letter after 3 tries
		factory.setErrorHandler(
				new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template), new FixedBackOff(0L, 2)));
		return factory;
	}

	@Bean
	public RecordMessageConverter converter() {
		return new StringJsonMessageConverter();
	}

	/**
	 * 쿠폰 Topic 생성.
	 *
	 * @return the new topic
	 */
	@Bean
	public NewTopic fcfsCouponTopic() {
		return new NewTopic(Constants.KAFKA_TOPIC_HOTDEAL_FCFS_COUPON, 1, (short) 1);
	}

	/**
	 * 응모 Topic 생성.
	 *
	 * @return the new topic
	 */
	@Bean
	public NewTopic pickTopic() {
		return new NewTopic(Constants.KAFKA_TOPIC_HOTDEAL_PICK, 3, (short) 1);
	}

	/**
	 * 선착순 Topic 생성.
	 *
	 * @return the new topic
	 */
	@Bean
	public NewTopic fcfsTopic() {
		return new NewTopic(Constants.KAFKA_TOPIC_HOTDEAL_FCFS, 3, (short) 1);
	}

}
