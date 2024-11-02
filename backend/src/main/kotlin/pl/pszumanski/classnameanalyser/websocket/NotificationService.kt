package pl.pszumanski.classnameanalyser.websocket

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import pl.pszumanski.classnameanalyser.dto.api.ApiResponse

@Service
class NotificationService(val messagingTemplate: SimpMessagingTemplate) {

    fun update(sessionId: String, message: ApiResponse) {
        messagingTemplate.convertAndSend("/topic/$sessionId/update", message)
    }
}