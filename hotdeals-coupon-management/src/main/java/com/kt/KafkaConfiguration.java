package com.kt;

/**
 * Kafka 연결 Configuration
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 7.0
 */
// @Configuration
public class KafkaConfiguration {

	// @Bean
	// public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
	// ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
	// ConsumerFactory<Object, Object> kafkaConsumerFactory, KafkaTemplate<Object, Object> template) {
	// ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new
	// ConcurrentKafkaListenerContainerFactory<>();
	// configurer.configure(factory, kafkaConsumerFactory);
	//
	// // dead-letter after 3 tries
	// factory.setErrorHandler(
	// new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template), new FixedBackOff(0L, 2)));
	// return factory;
	// }
	//
	// @Bean
	// public RecordMessageConverter converter() {
	// return new StringJsonMessageConverter();
	// }
	//
	// /**
	// * 선착순 Topic 생성.
	// *
	// * @return the new topic
	// */
	// @Bean
	// public NewTopic fcfsCouponTopic() {
	// return new NewTopic(Constants.KAFKA_TOPIC_HOTDEAL_FCFS_COUPON, 1, (short) 1);
	// }

}
