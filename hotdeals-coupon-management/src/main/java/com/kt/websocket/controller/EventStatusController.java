package com.kt.websocket.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 *
 * WebSocket Example URL : https://asfirstalways.tistory.com/359
 */
@Controller
public class EventStatusController {

	@MessageMapping("/event/fcfs/status")
	@SendTo("/topic/event")
	public boolean fcfsStatus() throws Exception {
		Thread.sleep(1000); // simulated delay
		Map<String, Object> result = new HashMap<>();
		result.put("event_id", "2020010101");
		result.put("fcfs_closed", false);
		return false;
	}

}
