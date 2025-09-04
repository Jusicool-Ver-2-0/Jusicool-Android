package com.jusicool.model.mapper.school

import com.jusicool.entity.school.SchoolInfoModel
import com.jusicool.model.school.SchoolInfoResponse
import com.jusicool.model.school.SchoolRow

fun SchoolInfoResponse.toModel(): List<SchoolInfoModel> {
    val rows = sections
        .orEmpty()
        .flatMap { it.row.orEmpty() }

    val dedup = LinkedHashMap<String, SchoolInfoModel>()

    rows.forEach { r ->
        val name = r.name?.trim().orEmpty()
        if (name.isEmpty()) return@forEach

        val address = listOfNotNull(
            r.roadAddr?.trim()?.takeIf { it.isNotEmpty() },
            r.roadDetail?.trim()?.takeIf { it.isNotEmpty() }
        ).joinToString(" ")

        val model = SchoolInfoModel(
            officeCode = r.officeCode.orEmpty(),
            schoolCode = r.schoolCode.orEmpty(),
            name = name,
            address = address
        )

        val key = if (model.schoolCode.isNotEmpty()) {
            "code:${model.schoolCode}"
        } else {
            "fallback:${model.name}|${model.address}"
        }

        dedup.putIfAbsent(key, model)
    }

    return dedup.values.toList()
}

fun SchoolRow.toModel(): SchoolInfoModel = SchoolInfoModel(
    officeCode = officeCode.orEmpty(),
    schoolCode = schoolCode.orEmpty(),
    name = name.orEmpty(),
    address = listOfNotNull(
        roadAddr?.trim()?.takeIf { it.isNotEmpty() },
        roadDetail?.trim()?.takeIf { it.isNotEmpty() }
    ).joinToString(" ")
)
