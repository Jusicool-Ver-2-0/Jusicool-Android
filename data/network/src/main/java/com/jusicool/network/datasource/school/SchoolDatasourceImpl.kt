package com.jusicool.network.datasource.school

import com.jusicool.model.mapper.school.toModel
import com.jusicool.model.school.SchoolInfoResponse
import com.jusicool.network.BuildConfig
import com.jusicool.network.api.SchoolApi
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SchoolDatasourceImpl @Inject constructor(
    private val schoolApi: SchoolApi
) : SchoolDataSource {
    override fun searchSchools(keyword: String, page: Int, size: Int): Flow<SchoolInfoResponse> =
        performApiRequest { schoolApi.searchSchools(key = BuildConfig.NEIS_API_KEY, pIndex = page, pSize = size, keyword = keyword) }
}