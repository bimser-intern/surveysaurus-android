package com.android.surveysaurus.model

import java.io.Serializable
import java.util.*

data class Comment (

    val comment: String,
    val commentID: Int,
    val report: Int,
    val upvote: Int,
    val surveytitle: String,
    val author: String,
    val deletable: Boolean,
    val time:Date

)





