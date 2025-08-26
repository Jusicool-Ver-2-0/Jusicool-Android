package com.jusicool.network.datasource.school

import com.jusicool.model.school.SchoolInfoResponse
import kotlinx.coroutines.flow.Flow

interface SchoolDataSource {
    fun searchSchools(keyword: String, page: Int = 1, size: Int = 30): Flow<SchoolInfoResponse>
}