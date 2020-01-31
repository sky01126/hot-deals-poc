package com.kt.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.kt.commons.config.Constants;
import com.kt.commons.persistence.model.HotdealsCoupon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@SuppressWarnings("all")
public class HotdealConsumer {

	public static HotdealsCoupon hotdealsCoupon;

	@KafkaListener(topics = Constants.KAFKA_TOPIC_HOTDEAL_FCFS_COUPON)
	public void onReceiving(@Header(KafkaHeaders.GROUP_ID) String groupId,
			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) Long offset,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, HotdealsCoupon coupon) {
		// log.debug("[ {} ] Topic: {}, Partition: {}, Offset: {}, Payload: {}", groupId, topic, partition, offset,
		// coupon.toJson());
		log.info("GroupID: {}, Payload: {}", groupId, coupon.toJson());
		HotdealConsumer.hotdealsCoupon = coupon;
	}

}
