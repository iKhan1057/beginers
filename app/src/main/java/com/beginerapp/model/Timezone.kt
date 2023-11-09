package com.beginerapp.model

import java.io.Serializable

data class Timezone(
    val description: String,
    val offset: String
):Serializable