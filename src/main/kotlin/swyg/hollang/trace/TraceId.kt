package swyg.hollang.trace

import java.util.UUID

class TraceId(val id: String = createId(), val level: Int = 0) {

    companion object {
        fun createId(): String {
            return UUID.randomUUID().toString().substring(0, 8)
        }
    }

    fun createNextId(): TraceId {
        return TraceId(id, level + 1)
    }

    fun createPreviousId(): TraceId {
        return TraceId(id, level - 1)
    }

    fun isFirstLevel(): Boolean {
        return level == 0
    }
}