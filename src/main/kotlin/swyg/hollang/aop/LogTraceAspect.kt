package swyg.hollang.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import swyg.hollang.trace.TraceStatus
import swyg.hollang.trace.logtrace.LogTrace

@Aspect
class LogTraceAspect(private val logTrace: LogTrace) {

    @Around("swyg.hollang.aop.LogTracePointcuts.allLogTrace()")
    fun execute(joinPoint: ProceedingJoinPoint): Any {
        //Advice
        var status: TraceStatus? = null
        try {
            val message = joinPoint.signature.toShortString()
            status = logTrace.begin(message)
            //로직 호출
            val result = joinPoint.proceed()
            logTrace.end(status)
            return result
        } catch (e: Exception) {
            logTrace.exception(status!!, e)
            throw e
        }
    }
}