package com.jusicool.repository

import com.jusicool.entity.school.SchoolInfoModel
import com.jusicool.model.mapper.school.toModel
import com.jusicool.network.datasource.school.SchoolDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SchoolRepositoryImpl @Inject constructor(
    private val schoolDataSource: SchoolDataSource
): SchoolRepository {
    override fun searchSchools(keyword: String, page: Int, size: Int): Flow<List<SchoolInfoModel>> {
        return schoolDataSource.searchSchools(keyword = keyword, page = page, size = size).map { it.toModel() }
    }
}