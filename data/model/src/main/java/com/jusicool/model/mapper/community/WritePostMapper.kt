package com.jusicool.model.mapper.community

import com.jusicool.entity.community.WritePostModel
import com.jusicool.model.community.WritePostRequest

fun WritePostModel.toDto() : WritePostRequest =
    WritePostRequest(
        title = this.title,
        content = this.content
    )