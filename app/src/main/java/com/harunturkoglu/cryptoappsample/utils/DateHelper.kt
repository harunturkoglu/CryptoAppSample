package com.harunturkoglu.cryptoappsample.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateHelper {

    private val dateFormatMap: MutableMap<String, DateFormat> =
        Collections.synchronizedMap(HashMap())

    // For the variable "stringDateFormat"
    private val stringDateFormat: DateFormat
        get() {
            return dateFormatMap.getOrPut(DatePattern.ddMMyyyy) {
                SimpleDateFormat(DatePattern.ddMMyyyy)
            }
        }

    // For the variable "uiDateFormat"
    private val uiDateFormat: DateFormat
        get() {
            return dateFormatMap.getOrPut(DatePattern.dd_MMMM) {
                SimpleDateFormat(DatePattern.dd_MMMM, Locale("tr"))
            }
        }

    // For unknown patterns that have Locale.US as its
    // 2nd argument
    private fun getFormat(format: String, locale: Locale = Locale.US): DateFormat {
        return dateFormatMap.getOrPut(format) {
            SimpleDateFormat(format, locale)
        }
    }

    fun String.toDate(): Date? {
        if (this.isEmpty()) return null
        val that = this
            .replace("'", "")
            .replace(" ", "T")
        val fullFormat = when (that.length) {
            15, 16 -> DatePattern.yyyyMMddTHHmm
            12, 13 -> DatePattern.yyyyMMddTHH
            10 -> DatePattern.yyyyMMdd_ServerFormat
            8 -> DatePattern.yyyyMMdd_WithoutSeparator
            14 -> DatePattern.yyyyMMddHHmmss_WithoutSeparator
            else -> DatePattern.yyyyMMddTHHmmss_JsonFormat
        }
        return that.toDate(fullFormat, Locale.US)
    }

    private fun String.toDate(format: String, locale: Locale): Date? {
        return try {
            getFormat(format, locale).parse(this)
        } catch (ex: Exception) {
            null
        }
    }

    fun Date?.toStringDate(): String? {
        return try {
            stringDateFormat.format(this)
        } catch (ex: Exception) {
            null
        }
    }

    fun Date?.toUiDate(): String? {
        return try {
            uiDateFormat.format(this)
        } catch (ex: Exception) {
            null
        }
    }
}

private object DatePattern {
    const val yyyyMMddTHHmmss_JsonFormat = "yyyy-MM-dd'T'HH:mm:ss"
    const val yyyyMMddTHHmm = "yyyy-MM-dd'T'HH:mm"
    const val ddMMyyyy = "dd.MM.yyyy"
    const val yyyyMMddTHH = "yyyy-MM-dd'T'HH"
    const val yyyyMMdd_ServerFormat = "yyyy-MM-dd"
    const val yyyyMMddHHmmss_WithoutSeparator = "yyyyMMddHHmmss"
    const val yyyyMMdd_WithoutSeparator = "yyyyMMdd"
    const val dd_MMMM = "dd MMMM"
}