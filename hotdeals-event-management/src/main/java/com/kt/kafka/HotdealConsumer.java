package com.kt.kafka;

import com.kt.commons.persistence.model.HotdealsCoupon;

//@Slf4j
//@Component
//@SuppressWarnings("all")
public class HotdealConsumer {

	public static HotdealsCoupon hotdealsCoupon;

	// @KafkaListener(topics = Constants.KAFKA_TOPIC_HOTDEAL_FCFS_COUPON)
	// public void onReceiving(@Header(KafkaHeaders.GROUP_ID) String groupId,
	// @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) Long offset,
	// @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, HotdealsCoupon coupon) {
	// log.info("GroupID: {}, Payload: {}", groupId, coupon.toJson());
	// HotdealConsumer.hotdealsCoupon = coupon;
	// }

}
