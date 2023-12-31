package com.rt.common

import co.touchlab.kermit.Logger

enum class LogTag(tag: String = "") {
    DEF_LOG("DEF_LOG"),
    UI_LOG("UI_LOG"),
    DATA_LOG("DATA_LOG"),
    DOMAIN_LOG("DOMAIN_LOG"),
    RAW_RESPONSE_LOG("RAW_RESPONSE_LOG")
}

interface ILog {

    val tag: String
        get() = LogTag.DEF_LOG.name

    fun d(msg: String = "") {
        Logger.withTag(tag).d(msg)
    }

    fun i(msg: String = "") {
        val finalMsg = buildString {
            append(msg)
            append(System.lineSeparator())
            append("==============================================================================")
            append(System.lineSeparator())
        }
        Logger.withTag(tag).i(finalMsg)
    }

    fun e(msg: String = "", throwable: Throwable? = null) {
        when (throwable) {
            null -> Logger.withTag(tag).e(msg)
            else -> Logger.withTag(tag).e(msg, throwable)
        }
    }

}

object UiLog : ILog {
    override val tag: String
        get() = LogTag.UI_LOG.name
}

object DataLog : ILog {
    override val tag: String
        get() = LogTag.DATA_LOG.name
}

object DomainLog : ILog {
    override val tag: String
        get() = LogTag.DOMAIN_LOG.name
}

object AppLog : ILog {
    override val tag: String
        get() = LogTag.DEF_LOG.name
}

object RawResponseLog : ILog {
    override val tag: String
        get() = LogTag.RAW_RESPONSE_LOG.name
}