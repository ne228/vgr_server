//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/")
//public class WebSocketMessageController {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private SimpMessagingTemplate template;
//
//    @Autowired
//    private Service service;
//
//    @Value("${ws.subscribe}")
//    private String subscribe;
//
//    @MessageMapping("${ws.mappingMessage}")
//    public void message(@Valid @Payload RequestMessage requestMessage, @Header("sessionId") String sessionId) {
//        getOverallMessage(sessionId, AppConstants.START_MESSAGE, AppConstants.START);
//        logger.debug("<<info:monitoring> WebSocket PROCESS IS STARTED for sessionId {}", sessionId);
//        try {
//            getOverallMessage(sessionId, AppConstants.IN_PROGRESS_MESSAGE, AppConstants.IN_PROGRESS);
//            service.authorize(requestMessage);
//            getOverallMessage(sessionId, AppConstants.SUCCESS_MESSAGE, AppConstants.SUCCESS);
//        } catch (Exception exp) {
//            getOverallMessage(sessionId, AppConstants.ERROR_MESSAGE, AppConstants.ERROR);
//            throw new Exception(exp.getMessage());
//        }
//    }
//
//    private void getOverallMessage(String sessionId, String message, String status) {
//        ResponseMessage responseMessage = new ResponseMessage();
//        responseMessage.setMessage(message);
//        responseMessage.setStatus(status);
//        sendMessageToUser(sessionId, responseMessage);
//    }
//
//    private void sendMessageToUser(String sessionId, ResponseMessage responseMessage) {
//        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//        headerAccessor.setSessionId(sessionId);
//        headerAccessor.setLeaveMutable(true);
//        this.template.convertAndSendToUser(sessionId, subscribe, responseMessage, headerAccessor.getMessageHeaders());
//    }
//
//}