package com.h1b4.www.handler;

 
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.h1b4.www.vo.Message;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
 
public class SocketHandler extends TextWebSocketHandler{
 
       private final Logger logger = LogManager.getLogger(getClass());
       private List<WebSocketSession> connectedUsers;
 
      
       public SocketHandler (){
             this.logger.info("create SocketHandler instance!");
             connectedUsers = new ArrayList<WebSocketSession>();
             
       }
 
       @Override
       public void afterConnectionClosed(WebSocketSession session,
                    CloseStatus status) throws Exception {
             super.afterConnectionClosed(session, status);
 
             this.logger.info("remove session!");
             connectedUsers.remove(session);
             
             for(WebSocketSession webSocketSession : connectedUsers){
            	 if(!session.getId().equals(webSocketSession.getId())){
            		 webSocketSession.sendMessage(new TextMessage(session.getRemoteAddress().getHostName() + "퇴장했습니다."));
            	 }
             }
       }
 
       @Override
       public void afterConnectionEstablished(WebSocketSession session)
                    throws Exception {
             super.afterConnectionEstablished(session);
             connectedUsers.add(session);
             this.logger.info(session.getId() + "님이 접속 했습니다.");
             this.logger.info("연결 IP : " + session.getRemoteAddress().getHostName());
       }
 
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	Message messageVO = Message.convertMessage(message.getPayload());
    	String hostName = "";
    	
    	for (WebSocketSession webSocketSession : connectedUsers){
    		if (messageVO.getType().equals("all")){
    			if(!session.getId().equals(webSocketSession.getId())){
    				webSocketSession.sendMessage(
    						new TextMessage("상대방 : "+ " -> " + messageVO.getMessage() ));
    										
    				//session.getRemoteAddress().getHostName()
    			}
    		}else{
    			hostName = webSocketSession.getRemoteAddress().getHostName();
    			if(messageVO.getTo().equals(hostName)){
    				webSocketSession.sendMessage(
    						new TextMessage(
    								"<span style='color:red; font-weight: bold;' >"
    								+"상대방 : "+ " -> " + messageVO.getMessage()
    								//session.getRemoteAddress().getHostName()
    								+ "</span>"));
    						
    				
    				break;
    			}
    		}
    	}
    }
 
}
