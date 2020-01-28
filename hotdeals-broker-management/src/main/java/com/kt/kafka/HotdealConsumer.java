package com.kt.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.kt.commons.config.Constants;
import com.kt.commons.persistence.model.HotdealEventPick;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HotdealConsumer {

	@KafkaListener(groupId = Constants.KAFKA_TOPIC_HOTDEALS, topicPattern = Constants.KAFKA_TOPIC_HOTDEALS + ".*")
	public void onReceiving(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) Long offset,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, HotdealEventPick event) {
		log.debug("[ {} ] Topic: {}, Partition: {}, Offset: {}, Payload: {}", Constants.KAFKA_TOPIC_HOTDEALS, topic,
				partition, offset, event.toJson());
	}

	// @KafkaListener(topics = Constants.KAFKA_TOPIC_HOTDEAL_PICK)
	// public void pickListen(HotdealEvent event) {
	// log.debug(">>> {}", event.toJson());
	// }

}
