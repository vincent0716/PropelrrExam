package com.vmcruz.propelrrexam;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("1002f950-f855-47ea-8054-78c246e7c6e6")
    @FormUrlEncoded
    Call<ApiResponse> saveInfo(@Field("fullName") String fullname,
                          @Field("email") String email,
                          @Field("mobileNumber") String mobileNumber,
                          @Field("age") String age
                          );

}
