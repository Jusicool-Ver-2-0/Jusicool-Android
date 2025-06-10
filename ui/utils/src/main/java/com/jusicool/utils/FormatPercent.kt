package com.jusicool.utils

object FormatPercent {

    /**
     * 숫자를 퍼밀(%) 형식으로 반환
     * ex: +23.2‰, -1.0‰
     */
    fun format(value: Double): String {
        return String.format(
            "%+.1f%",
            value
        )
    }

    fun format(value: Float): String = format(value.toDouble())
    fun format(value: Int): String = format(value.toDouble())
    fun format(value: Long): String = format(value.toDouble())
}