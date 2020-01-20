package com.kt.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CouponschedulerExecutor {

	/**
	 * 스케줄 실행
	 */
	@Scheduled(initialDelay = 60 * 1000, fixedRate = 10 * 60 * 1000)
	protected void execute() {
		log.debug("[ RUN ] Coupon Schedule Executer...");
	}

}
