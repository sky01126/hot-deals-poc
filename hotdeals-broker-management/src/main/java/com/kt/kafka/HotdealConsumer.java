package com.kt.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.kt.commons.config.Constants;
import com.kt.commons.persistence.model.HotdealEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HotdealConsumer {

	@KafkaListener(id = Constants.KAFKA_CONSUMER_FCFS_GROUP_ID, topicPattern = Constants.KAFKA_TOPIC_HOTDEAL + ".*")
	public void onReceiving(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) Long offset,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, HotdealEvent event) {
		log.debug("[ {} ] Topic: {}, Partition: {}, Offset: {}, Payload: {}", Constants.KAFKA_TOPIC_HOTDEAL, topic,
				partition, offset, event.toJson());
	}
	//
	// @KafkaListener(id = Constants.KAFKA_CONSUMER_PICK_GROUP_ID, topics = Constants.KAFKA_TOPIC_HOTDEAL_PICK)
	// public void pickListen(HotdealEvent event) {
	// log.debug(">>> {}", event.toJson());
	// }

}
