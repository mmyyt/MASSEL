package com.massel.www.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

	
	//현재 연결된 세션 리스트
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();

	//새로운 WebSocket세션이 열릴때 호출
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		//사용자가 연결되었을 때의 로직
		log.info("@@@@@chat connection established@@@@@");
		
		sessionList.add(session);
		log.info("새로운 사용자 연결 : "+session.getId());  //getId > 고유 아이디
		//sessionList.size()로 현재 연결된 사용자 수를 알 수 있음
	}
	
	//클라이언트로부터 메세지를 수신했을 때 호출
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		log.info("@@@@@handle text message@@@@@");
		
		//수신한 메세지 처리
		log.info("< "+session.getId()+" > 에게 < "+message.getPayload()+" > 메세지 받음");
		//message.getPayload()  : TextMessage 객체의 메서드로 수신한 메세지의 실제 내용 반환
		//반환되는 값은 String 타입, WebSocket을 통해 전달된 텍스트 데이터 포함
		
		//전송된 메세지를 List에 담긴 세션에 전송
		for(WebSocketSession ses : sessionList) {
			ses.sendMessage(new TextMessage(message.getPayload()));
		}
	}
	
	//Websocket 세션이 닫힐 때 호출
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		//사용자가 연결을 종료했을 때
		log.info("@@@@@after connection closed@@@@@");
		//session에서 나간 클라이언트 정보 제거
		sessionList.remove(session);
		log.info(session.getId()+" 의 연결이 끊겼습니다.");
	}
	
}


