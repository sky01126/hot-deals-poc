/*
 * Copyright(C) 2017 KT Hitel Co., Ltd. all rights reserved.
 *
 * This is a proprietary software of KTH corp, and you may not use this file except in
 * compliance with license with license agreement with KTH corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of KTH corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 */

package com.kt;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot Application
 *
 * @author <a href="mailto:ky.son@kt.com"><b>손근양</b></a>
 * @version 1.0.0
 * @since 8.0
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = { "com.kthcorp.commons", "com.kt" })
public class Application extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	/**
	 * Spring boot application main method.
	 *
	 * @param args the string array
	 */
	public static void main(String[] args) {
		log.info("Application start...");
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Scheduler Executor
	 *
	 * @return Executor
	 */
	@Bean
	public Executor schedulerExecutor() {
		log.debug("Scheduled Thread Pool Executor...");
		return new ScheduledThreadPoolExecutor(1);
	}

	// /**
	// * Thread Pool Task Executor
	// *
	// * @return ThreadPoolTaskExecutor
	// */
	// @Bean
	// public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
	// log.debug("Thread Pool Task Executor...");
	// final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	// executor.setCorePoolSize(1);
	// executor.setMaxPoolSize(1);
	// return executor;
	// }

}
