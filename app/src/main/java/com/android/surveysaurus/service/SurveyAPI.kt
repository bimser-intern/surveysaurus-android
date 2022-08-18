package com.android.surveysaurus.service

import com.android.surveysaurus.model.FillModel
import com.android.surveysaurus.model.LoginModel
import com.android.surveysaurus.model.SignUpModel
import com.android.surveysaurus.model.SurveyModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SurveyAPI {

    //POST , GET ,UPDATE ,DELETE

    @POST("api/user/register")
    fun postSignUp(@Body signUpModel: SignUpModel): Call<SignUpModel>

    @POST("api/user/login")
    fun postLogin(@Body loginModel: LoginModel): Call<ResponseBody>

    @POST("api/survey/createSurvey")
    fun postCreateSurvey(@Body surveyModel: SurveyModel,@Header("authorization") token: String): Call<ResponseBody>

    @POST("api/survey/fillSurvey")
    fun postFillSurvey(@Body fillModel: FillModel ,@Header("authorization") token: String): Call<ResponseBody>

    @GET("api/user/mysurveys")
    fun getMySurvey(@Header("authorization") token: String): Call<ResponseBody>

    @GET("api/survey/sample")
    fun getSurveys(@Header("authorization") token: String): Call<ResponseBody>
}