package swyg.hollang.trace.logtrace

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import swyg.hollang.trace.TraceId
import swyg.hollang.trace.TraceStatus

@Component
class ThreadLocalLogTrace: LogTrace {

    private val logger = LoggerFactory.getLogger(ThreadLocalLogTrace::class.java)
    private val traceIdHolder: ThreadLocal<TraceId> = ThreadLocal()

    companion object {
        const val START_PREFIX: String = "-->"
        const val COMPLETE_PREFIX: String = "<--"
        const val EX_PREFIX: String = "<X--"
    }

    override fun begin(message: String): TraceStatus {
        syncTraceId()
        val traceId = traceIdHolder.get()
        val startTimeMs = System.currentTimeMillis()
        val prefix = addSpace(START_PREFIX, traceId.level)
        logger.info("[{}] {}{}", traceId.id, prefix, message)
        return TraceStatus(traceId, startTimeMs, message)
    }

    private fun syncTraceId() {
        traceIdHolder.set(
            when(val traceId = traceIdHolder.get()){
                null -> TraceId()
                else -> traceId.createNextId()
            }
        )
    }

    override fun end(status: TraceStatus) {
        complete(status, null)
    }

    override fun exception(status: TraceStatus, exception: Exception) {
        complete(status, exception)
    }

    private fun complete(status: TraceStatus, e: Exception?) {
        val stopTimeMs = System.currentTimeMillis()
        val resultTimeMs: Long = stopTimeMs - status.startTimeMs
        val traceId: TraceId = status.traceId
        val prefix = when(e){
            null -> addSpace(COMPLETE_PREFIX, traceId.level)
            else -> addSpace(EX_PREFIX, traceId.level)
        }
        val logMsg = "[${traceId.id}] ${prefix}${status.message} time=${resultTimeMs}ms"
        when(e){
            null -> logger.info(logMsg)
            else -> logger.error("$logMsg ex={}", e.toString())
        }
        releaseTraceId()
    }

    private fun releaseTraceId() {
        val traceId = traceIdHolder.get()
        if (traceId.isFirstLevel()) {
            //TODO: 항상 스레드 로컬을 사용하면 제거하자!
            //쓰레드를 재사용하는 톰캣의 경우 문제가 발생할 수 있다.
            traceIdHolder.remove() //destroy
        } else {
            traceIdHolder.set(traceId.createPreviousId())
        }
    }

    private fun addSpace(prefix: String, level: Int): String {
        return buildString {
            for(i in 0 until level){
                append(if (i == level - 1) "|$prefix" else "|   ")
            }
        }
    }

}