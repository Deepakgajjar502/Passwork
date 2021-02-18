package com.b2b.passwork.retrofit;


import com.b2b.passwork.Model.DefaultResponse;
import com.b2b.passwork.Model.FloorList.FloorListResponse;
import com.b2b.passwork.Model.Login.LoginResponse;
import com.b2b.passwork.Model.ProfileResponse;
import com.b2b.passwork.Model.SeatBookResponse;
import com.b2b.passwork.Model.SeatList.SeatListResponse;
import com.b2b.passwork.Model.WorkspaceList.workspaceListResponse;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {


    @POST(Constant.LIST_lOGIN)
    Call<LoginResponse> Login(@Body RequestBody body);

    @POST(Constant.USER_PROFILE)
    Call<ProfileResponse> getProfile(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.USER_PROFILE_UPDATE)
    Call<DefaultResponse> getUpdateProfile(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.USER_PASSWORD_UPDATE)
    Call<DefaultResponse> getUpdatepassword(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.USER_WORKSPACE_LIST)
    Call<workspaceListResponse> getWorkspaceList(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_FLOOR_LIST)
    Call<FloorListResponse> getFloorList(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_SEATS_LIST)
    Call<SeatListResponse> getSeatList(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_SAVE_BOOKING)
    Call<SeatBookResponse> getSAVEBOOK(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_UPCOMING_BOOKING)
    Call<SeatBookResponse> getUpcommingBooking(@Header("Authorization") String token, @Body RequestBody body);

}


