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
        val START_PREFIX: String = "-->"
        val COMPLETE_PREFIX: String = "<--"
        val EX_PREFIX: String = "<X--"
    }

    override fun begin(message: String): TraceStatus {
        syncTraceId()
        val traceId = traceIdHolder.get()
        val startTimeMs = System.currentTimeMillis()
        logger.info(
            "[{}] {}{}", traceId.id, addSpace(
                START_PREFIX,
                traceId.level
            ), message
        )
        return TraceStatus(traceId, startTimeMs, message)
    }

    private fun syncTraceId() {
        val traceId = traceIdHolder.get()
        if (traceId == null) {
            traceIdHolder.set(TraceId())
        } else {
            traceIdHolder.set(traceId.createNextId())
        }
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
        if (e == null) {
            logger.info(
                "[{}] {}{} time={}ms", traceId.id,
                addSpace(COMPLETE_PREFIX, traceId.level), status.message,
                resultTimeMs
            )
        } else {
            logger.info(
                "[{}] {}{} time={}ms ex={}", traceId.id,
                addSpace(EX_PREFIX, traceId.level), status.message, resultTimeMs,
                e.toString()
            )
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
        val sb = StringBuilder()
        for (i in 0 until level) {
            sb.append(if (i == level - 1) "|$prefix" else "|   ")
        }
        return sb.toString()
    }


}