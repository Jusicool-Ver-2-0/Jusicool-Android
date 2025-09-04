package com.jusicool.repository

import com.jusicool.entity.school.SchoolInfoModel
import kotlinx.coroutines.flow.Flow

interface SchoolRepository {
    fun searchSchools(keyword: String, page: Int = 1, size: Int = 30): Flow<List<SchoolInfoModel>>
}