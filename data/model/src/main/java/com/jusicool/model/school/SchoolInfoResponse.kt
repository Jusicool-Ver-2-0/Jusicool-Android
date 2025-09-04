package com.jusicool.model.school

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SchoolInfoResponse(
    @Json(name = "schoolInfo") val sections: List<RowSection>? = null
)

@JsonClass(generateAdapter = true)
data class RowSection(
    val row: List<SchoolRow>? = null
)

@JsonClass(generateAdapter = true)
data class SchoolRow(
    @Json(name = "ATPT_OFCDC_SC_CODE") val officeCode: String? = null,
    @Json(name = "SD_SCHUL_CODE") val schoolCode: String? = null,
    @Json(name = "SCHUL_NM") val name: String? = null,
    @Json(name = "ORG_RDNMA") val roadAddr: String? = null,
    @Json(name = "ORG_RDNDA") val roadDetail: String? = null
)
