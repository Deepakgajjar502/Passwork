package com.b2b.passwork.retrofit;


import com.b2b.passwork.Model.Category.CategoryResponse;
import com.b2b.passwork.Model.DefaultResponse;
import com.b2b.passwork.Model.Employee.EmployeeResponse;
import com.b2b.passwork.Model.FloorList.FloorListResponse;
import com.b2b.passwork.Model.Login.LoginResponse;
import com.b2b.passwork.Model.MeetingBookResponse;
import com.b2b.passwork.Model.PollList.PollListResponse;
import com.b2b.passwork.Model.ProfileResponse;

import com.b2b.passwork.Model.Room.GetBay.NewGetBay.NewGetBayResponse;
import com.b2b.passwork.Model.Room.GetRoomResponse;
import com.b2b.passwork.Model.SeatBookResponses;

import com.b2b.passwork.Model.SeatList.SeatListResponse;
import com.b2b.passwork.Model.SubCategory.SubCateogryResponse;
import com.b2b.passwork.Model.TicketDetail.TicketDetailResponse;
import com.b2b.passwork.Model.Upcoming.UpComingResponse;
import com.b2b.passwork.Model.WorkspaceList.workspaceListResponse;
import com.b2b.passwork.Model.pollAnswerModel.PollAnsResponse;
import com.b2b.passwork.Model.submitTicket.TicketResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
    Call<SeatBookResponses> getSAVEBOOK(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_SAVE_BOOKING)
    Call<MeetingBookResponse> getMEETINGBOOK(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_UPCOMING_BOOKING)
    Call<UpComingResponse> getUpcommingBooking(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_HISOTRY_BOOKING)
    Call<UpComingResponse> getHisotryBooking(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_ROOM)
    Call<GetRoomResponse> getRoomList(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_ROOM)
    Call<NewGetBayResponse> getBayList(@Header("Authorization") String token, @Body RequestBody body);

    @GET("workspace/details-employees-by-corporate-id/{id}")
    Call<EmployeeResponse> getEmployeeList(@Header("Authorization") String token, @Path("id") String id);

    @POST(Constant.GET_CHECK_IN)
    Call<DefaultResponse> getCheckIn(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_POLL_LIST)
    Call<PollListResponse> GetPollList(@Header("Authorization") String token, @Body RequestBody body);


    @POST(Constant.GET_POLL_RESULT)
    Call<PollListResponse> GetPollResult(@Header("Authorization") String token);

    @POST(Constant.GET_POLL_SAVE)
    Call<PollAnsResponse> getPollSave(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_CATEGORY)
    Call<CategoryResponse> getCatoegry(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_SUBCATEGORY)
    Call<SubCateogryResponse> getSubCatoegry(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_SUB_TICKET)
    Call<TicketResponse> getTicketSave(@Header("Authorization") String token, @Body RequestBody body);

    @POST(Constant.GET_MY_TICKET)
    Call<TicketDetailResponse> getTicket(@Header("Authorization") String token);

}


