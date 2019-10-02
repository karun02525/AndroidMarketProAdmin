package com.market.admin.model
import com.google.gson.annotations.SerializedName


data class ResponseModel(
    @SerializedName("message")
    var message: String?,
    @SerializedName("data")
    var result: List<ResultData>?,
    @SerializedName("status")
    var status: Boolean?
)

data class ResultData(
    @SerializedName("category")
    val category: String,
    @SerializedName("create_at")
    val createAt: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("is_verify")
    val isVerify: Int,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("update_at")
    val updateAt: String,
    @SerializedName("vender_id")
    val venderId: String
)