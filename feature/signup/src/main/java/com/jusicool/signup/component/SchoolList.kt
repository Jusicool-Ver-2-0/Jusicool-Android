package com.jusicool.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class School(
    val name: String,
    val address: String
)

@Composable
fun SchoolList(
    schools: List<School>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(schools) { school ->
            SchoolListItem(
                schoolName = school.name,
                address = school.address
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SchoolListPreview() {
    val sampleSchools = listOf(
        School("광주소프트웨어마이스터고등학교", "광주광역시 광산구 하남산단6번로 107"),
        School("서울과학고등학교", "서울특별시 노원구 공릉로 232"),
        School("한성과학고등학교", "서울특별시 종로구 창경궁로 254"),
        School("대전과학고등학교", "대전광역시 유성구 장동 23"),
        School("부산과학고등학교", "부산광역시 남구 신선로 365")
    )
    SchoolList(schools = sampleSchools)
}
