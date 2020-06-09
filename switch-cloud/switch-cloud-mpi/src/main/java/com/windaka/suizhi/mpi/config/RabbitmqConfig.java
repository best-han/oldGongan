package com.windaka.suizhi.mpi.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * rabbitmq配置
 * @author hjt
 */
@Slf4j
@Configuration
public class RabbitmqConfig {

	/*@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(UserCenterMq.MQ_EXCHANGE_USER);
	}
	@Bean
	public Queue Queue(){
		return new Queue("hello");
	}

	@Autowired
	private AmqpTemplate rabbitTemplate;
	public void send(){
		this.rabbitTemplate.convertAndSend("","");
	}*/

	//这里设置很多个EXCHANGE,QUEUE,ROUTINGKEY，是为了接下来的不同使用场景
/*	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.port}")
	private int port;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;*/
/*	public static final String EXCHANGE_TIPS = "my-mq-exchange_TIPS";
	public static final String EXCHANGE_B = "my-mq-exchange_B";
	public static final String EXCHANGE_C = "my-mq-exchange_C";*/
	public static final String EXCHANGE_POLICE_FACEALARM = "my-mq-exchange_POLICE_FACEALARM";
	public static final String QUEUE_TIPS = "QUEUE_TIPS";
	public static final String QUEUE_PICS = "QUEUE_PICS";
	public static final String QUEUE_STREET_ABNORMAL = "QUEUE_STREET_ABNORMAL";
	public static final String QUEUE_POLICE_FACEALARM = "QUEUE_POLICE_FACEALARM";
	public static final String QUEUE_YS_FACEALARM = "QUEUE_YS_FACEALARM";
/*	public static final String QUEUE_B = "QUEUE_B";
	public static final String QUEUE_C = "QUEUE_C";
	public static final String ROUTINGKEY_TIPS = "spring-boot-routingKey_TIPS";
	public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
	public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";*/
	public static final String ROUTINGKEY_POLICE_FACEALARM = "spring-boot-routingKey_POLICE_FACEALARM";

/*	@Bean
	public ConnectionFactory connectionFactory(){
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost("/");
		connectionFactory.setPublisherConfirms(true);
		return connectionFactory;
	}*/
/*	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	//必须是prototype类型
	protected RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		return template;
	}*/

	/**
	 * 把交换机，队列，通过路由关键字进行绑定，写在RabbitConfig类当中
	 * * 注意：针对消费者配置****************************************************放在消费者（接收端的配置）上边的相同
	 *  1. 设置交换机类型
	 *  2. 将队列绑定到交换机
	 *  FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
	 *  HeadersExchange ：通过添加属性key-value匹配
	 *  DirectExchange:按照routingkey分发到指定队列
	 *  TopicExchange:多关键字匹配
	 */
/*	@Bean
	public DirectExchange defaultExchange() {
		return new DirectExchange(EXCHANGE_TIPS,true,false);
	}*/
	@Bean
	public DirectExchange defaultExchange() {
		return new DirectExchange(EXCHANGE_POLICE_FACEALARM,true,false);
	}
	/**
	 *  * 获取队列Tips
	 *  * @return
	 *  */
	@Bean
	public Queue queueTips() {
	 	return new Queue(QUEUE_TIPS, true); //队列持久
	 }
	@Bean
	public Queue queuePics() {
		return new Queue(QUEUE_PICS, true); //队列持久
	}
	@Bean
	public Queue queueFaceAlarm() {
		return new Queue(QUEUE_POLICE_FACEALARM, true); //队列持久
	}

	/**
	 *  * 获取队列B
	 *  * @return
	 *  */
/*	@Bean
	public Queue queueB() {
		return new Queue(QUEUE_B, true); //队列持久
	}*/
	/**
	 *  * 获取队列C
	 *  * @return
	 *  */
/*	@Bean
	public Queue queueC() {
		return new Queue(QUEUE_C, true); //队列持久
	}*/
	//一个交换机可以绑定多个消息队列
	// 也就是消息通过一个交换机，可以分发到不同的队列当中去。
/*	@Bean
	public Binding binding() {
	 	return BindingBuilder.bind(queueTips()).to(defaultExchange()).with(RabbitmqConfig.ROUTINGKEY_TIPS);
	 } */
	@Bean
	public Binding bindingFaceAlarm(){
	 	return BindingBuilder.bind(queueFaceAlarm()).to(defaultExchange()).with(RabbitmqConfig.ROUTINGKEY_POLICE_FACEALARM);
	 }

	/*另外一种消息处理机制的写法如下，在RabbitMQConfig类里面增加bean：*/
//	@Bean
//	public SimpleMessageListenerContainer messageContainer() {
//		//加载处理消息Tips的队列
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//		// 设置接收多个队列里面的消息，这里设置接收队列Tips
//		//container.setQueues(queueTips());
//		// 假如想一个消费者处理多个队列里面的信息可以如下设置：
//		container.setQueues(queueTips(),queueB(),queueC());
//		container.setExposeListenerChannel(true);
//		// 设置最大的并发的消费者数量
//		container.setMaxConcurrentConsumers(10);
//		// 最小的并发消费者的数量
//		container.setConcurrentConsumers(1);
//		// 设置确认模式手工确认
//		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//		container.setMessageListener(new ChannelAwareMessageListener() {
//			@Override
//			public void onMessage(Message message, Channel channel) throws Exception {
//				/**通过basic.qos方法设置prefetch_count=1，这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
//				 *  换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它 */
//				channel.basicQos(1);
//				byte[] body = message.getBody();
//				log.info("接收处理队列Tips当中的消息:" + new String(body));
//				/**为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
//				 *  当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，然后RabbitMQ才会将消息删除。*/
//				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//			}
//		});
//		return container;
//	}




}
