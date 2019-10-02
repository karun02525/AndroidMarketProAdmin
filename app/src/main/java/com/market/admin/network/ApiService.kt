package com.market.admin.network

import com.market.admin.model.ResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {


    @GET("/admin/get-pending-vender")
    fun getVenderList(): Observable<ResponseModel>

    @GET("/admin/verify")
    fun getVerify(@Query("uid") uid: String?,
                  @Query("vender_id") vender_id: String?,
                  @Query("is_verify") is_verify: Int): Observable<ResponseModel>

}