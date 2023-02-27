package swyg.hollang.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.util.*

class LogInterceptor: HandlerInterceptor {

    private val logger = LoggerFactory.getLogger(LogInterceptor::class.java)

    companion object {
        val LOG_ID: String = "logId"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestURI = request.requestURI
        val uuid = UUID.randomUUID().toString()

        request.setAttribute(LOG_ID, uuid)

        logger.info("[REQUEST] [{}][{}]", uuid, requestURI)

        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        val requestURI = request.requestURI
        val logId = request.getAttribute(LOG_ID) as String

        logger.info("[RESPONSE] [{}][{}][{}]", logId, response.getStatus(), requestURI)
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        if(ex != null){
            logger.error("[EXCEPTION] [{}][{}]", response.status, ex.message)
        }
    }
}