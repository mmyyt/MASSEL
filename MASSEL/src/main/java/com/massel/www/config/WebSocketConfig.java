package com.massel.www.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		//메시지 브로커 설정
		config.enableSimpleBroker("/topic", "/queue");
		config.setApplicationDestinationPrefixes("/app"); //클라이언트가 서버에 보낼 메세지 prefix
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//클라이언트가 연결할 WebSocket 엔드포인트 설정
		registry.addEndpoint("/ws")
		.setAllowedOrigins("http://localhost:8080") // 허용할 도메인을 명시적으로 설정
		.withSockJS(); //SockJs -> WebSocket을 지원하지 않는 브라우저에서도 동작할 수 있게 설정
	}
	
	
}
