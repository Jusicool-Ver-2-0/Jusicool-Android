package com.jusicool.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.entity.school.SchoolInfoModel
import com.jusicool.signup.viewModel.uiState.SearchSchoolUiState

@Composable
fun SchoolList(
    schools: SearchSchoolUiState.Success,
    selectedSchool: SchoolInfoModel?,
    onSelectSchool: (SchoolInfoModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(schools.school) { school ->
            SchoolListItem(
                modifier = Modifier.JusicoolClickable { onSelectSchool(school) },
                schoolName = school.name,
                address = school.address,
                selected = (selectedSchool?.name == school.name)
            )
        }
    }
}