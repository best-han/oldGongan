package com.windaka.suizhi.api.log.constants;

/**
 * 云税慧商Log日志rabbitmq的exchange和routing key定义
 * @author hjt
 *
 */
public interface LogCenterMq {

	/**
	 * OSS接收日志的队列名
	 */
	String OSS_LOG_QUEUE = "oss.log.queue";

	/**
	 * plat接收日志的队列名
	 */
	String PLAT_LOG_QUEUE = "plat.log.queue";

	/**
	 * rabbitmq交换机
	 */
	String MQ_EXCHANGE_LOG = "yshs.log.topic.exchange";

	/**
	 *OSS日志系统日志记录routing key
	 */
	String OSS_ROUTING_KEY_LOG_SAVE = "oss.log.save";

	/**
	 * PLAT日志系统日志记录routing key
	 */
	String PLAT_ROUTING_KEY_LOG_SAVE = "plat.log.save";
}
