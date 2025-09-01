package com.jusicool.usecase.school

import com.jusicool.entity.school.SchoolInfoModel
import com.jusicool.repository.SchoolRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchSchoolUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository
) {
    operator fun invoke(keyword: String, page: Int = 1, size: Int = 30): Flow<List<SchoolInfoModel>> =
        schoolRepository.searchSchools(keyword = keyword, page = page, size = size)
}