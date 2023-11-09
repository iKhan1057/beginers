package com.beginerapp.model

import java.io.Serializable

data class UserParentModel(
    val info: Info,
    val results: List<Result>
):Serializable