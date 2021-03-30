package com.example.animal.configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
	// MessageConverter Bean 추가 
	// 서비스에서 리턴되는게 객체 타입이기 때문에 json 타입으로 나갈 수 있게 설정해줌
	@Bean
	public MessageConverter rabbitMessageConverter() {
		// json -> java object
		return new Jackson2JsonMessageConverter();
	}
}
