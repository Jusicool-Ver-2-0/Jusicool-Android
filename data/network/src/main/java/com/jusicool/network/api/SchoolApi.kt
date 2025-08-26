package com.jusicool.network.api


import com.jusicool.model.school.SchoolInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolApi {
    @GET("schoolInfo")
    suspend fun searchSchools(
        @Query("KEY")    key: String,
        @Query("Type")   type: String = "json",
        @Query("pIndex") pIndex: Int = 1,
        @Query("pSize")  pSize: Int = 30,
        @Query("SCHUL_NM") keyword: String
    ): SchoolInfoResponse
}
