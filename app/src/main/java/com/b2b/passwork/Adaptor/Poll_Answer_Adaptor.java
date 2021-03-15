package com.b2b.passwork.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2b.passwork.Fragment.BookMeeting;
import com.b2b.passwork.Fragment.OnGoingPollFragment;
import com.b2b.passwork.Fragment.ResultPollFragment;
import com.b2b.passwork.Model.AnswerModel;
import com.b2b.passwork.Model.DefaultResponse;
import com.b2b.passwork.Model.PollList.OptionsItem;
import com.b2b.passwork.Model.PollList.QuestionsItem;
import com.b2b.passwork.Model.PollList.pollDataItem;
import com.b2b.passwork.Model.pollAnswerModel.PollAnsOptionsItem;
import com.b2b.passwork.Model.pollAnswerModel.PollAnsResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.StaticUtil;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.interfaces.OnItemClickListener;
import com.b2b.passwork.retrofit.RestManager;
import com.gdacciaro.iOSDialog.iOSDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Poll_Answer_Adaptor extends RecyclerView.Adapter {
    Context context;
    // List<CategoryInformation> arrayList;


    List<OptionsItem> AnswerList;
    List<PollAnsOptionsItem> POllAnswerList = new ArrayList<>();
    String fragment, pollId, QusId, TOtalAns;
    List<String> selectedAns = new ArrayList<>();;
    List<String> selectedAnswer = new ArrayList<>();;
    Button btnSubmit;
    private OnItemClickListener onItemClickListener;
    String UserId, Token;
    UserSessionManager session;
    Boolean questType;
    ProgressBar progressBar;
    OnGoingPollFragment Activity;

    public Poll_Answer_Adaptor(Context activity, List<OptionsItem> rooms, String fragment, String pollId, String QusId, String TotalAns, Button btnSubmit, boolean questionType, ProgressBar progressBar) {
        this.context =  activity;
        this.AnswerList = rooms;
        this.fragment = fragment;
        this.pollId = pollId;
        this.QusId = QusId;
        this.TOtalAns = TotalAns;
        this.btnSubmit = btnSubmit;
        this.questType = questionType;
        this.progressBar = progressBar;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_anwser, viewGroup, false);
        return new Poll_Answer_Adaptor.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        try {
            session = new UserSessionManager(context);
            HashMap<String, String> user = session.getUserDetails();
            Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
            UserId = user.get(UserSessionManager.KEY_ID);
         //   Log.e("framgnet", fragment);
            Log.e("get_Ans", AnswerList.get(position).getAnswerText());
            ((Poll_Answer_Adaptor.viewHolder)holder).txtTv_option.setText(AnswerList.get(position).getAnswerText()+"");
          //  ((Poll_Answer_Adaptor.viewHolder)holder).txtTv_percent.setText("End Date :"+rollList.get(position).getEndDate()+"");

            ((viewHolder)holder).seekBar.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    return true;
                }
            });


            if(fragment.equals("result")){

                int CountAns = Integer.parseInt(AnswerList.get(position).getCount());
                int TotalAnswer = Integer.parseInt(TOtalAns);

                int ansPer = (CountAns*100)/TotalAnswer;
                ((viewHolder)holder).seekBar.setProgress(ansPer);


                ((Poll_Answer_Adaptor.viewHolder)holder).txtTv_percent.setText(ansPer+"%");

                if(AnswerList.get(position).isAnswerByUser()){
                    ((Poll_Answer_Adaptor.viewHolder)holder).txtTv_percent.setTextColor(context.getResources().getColor(R.color.light_blue));
                    ((viewHolder)holder).seekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#BFF3FA")));
                    ((Poll_Answer_Adaptor.viewHolder)holder).txtTv_option.setTextColor(context.getResources().getColor(R.color.light_blue));
                }


            }else if(fragment.equals("ongoing")){

                ((viewHolder)holder).seekBar.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        ((viewHolder)holder).seekBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#BFF3FA")));
                        ((Poll_Answer_Adaptor.viewHolder)holder).txtTv_option.setTextColor(context.getResources().getColor(R.color.light_blue));


                            if(questType){

                                callSaveAPI(UserId, QusId, pollId, AnswerList.get(position).getOfferedAnswerId());
                            }else {
                                selectedAns.add(AnswerList.get(position).getOfferedAnswerId());
                            }


                        return true;
                    }
                });





                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                      //  ArrayList<String> values=new ArrayList<String>();
                        HashSet<String> hashSet = new HashSet<String>();
                        hashSet.addAll(selectedAns);
                        selectedAns.clear();
                        selectedAns.addAll(hashSet);

                        String listString = "";
                        for (String s : selectedAns)
                        {

                            listString += s + ",";
                        }

                   String finalString   =  listString.substring( 0, listString.length() - 1 );

                      callSaveAPI(UserId, QusId, pollId, finalString);
                    /*    Log.e("poll_offered_answer_id", finalString);
                        Log.e("poll_id", pollId);
                        Log.e("poll_question_id", QusId);
                        Log.e("customer_id", UserId);*/
                    }
                });



            }




        }catch (Exception ex){
            Log.d("Sri","ex"+ex);
        }
    }

    private void callSaveAPI(String userId, String qusId, String pollId, String finalString) {

        progressBar.setVisibility(View.VISIBLE);

        String token = "Bearer " + Token;
        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("poll_id", pollId);
        jsonParams.put("poll_question_id", qusId);
        jsonParams.put("poll_offered_answer_id", finalString);
        jsonParams.put("customer_id", userId);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());

        Call<PollAnsResponse> responseBody = RestManager.getInstance().getService()
                .getPollSave(token, body);
        //"artist",
        responseBody.enqueue(new Callback<PollAnsResponse>() {
            @Override
            public void onResponse(Call<PollAnsResponse> call, Response<PollAnsResponse> response ) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
               progressBar.setVisibility(View.GONE);

                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        PollAnsResponse   response1 = response.body();

                        if (response1.getStatus()==1) {



                            POllAnswerList = response1.getData().getOptions();

                            //StaticUtil.showToast(FragmentManager.ge, "Password update successfully");



                            final iOSDialog iOSDialog = new iOSDialog(context);
                            iOSDialog.setTitle(context.getString(R.string.app_name));
                            iOSDialog.setSubtitle("Your request has been saved successfully");
                            iOSDialog.setPositiveLabel("OK");
                            //iOSDialog.setNegativeLabel(getActivity().getString(R.string.Lbl_Cancel));
                            iOSDialog.setBoldPositiveLabel(true);

                            iOSDialog.setPositiveListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                //    loadFragment(new ResultPollFragment());
                                    iOSDialog.dismiss();
                                }
                            });
                            iOSDialog.show();

                        } else {

                         //   StaticUtil.showIOSLikeDialog(context, "Something went wrong");

                        }

                    } else {


                     //   StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");


                    }

                } else {
                //    StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");

                }


            }

            @Override
            public void onFailure(Call<PollAnsResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
              //  progressBar.setVisibility(View.GONE);
               // StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");


            }
        });








    }


    @Override
    public int getItemCount() {

        return AnswerList.size();
    }




    class viewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {


        TextView txtTv_percent;
        TextView txtTv_option;
        SeekBar seekBar;
        RelativeLayout AnwLayout;



        public viewHolder(View itemView) {
            super(itemView);
            seekBar =   itemView.findViewById(R.id.seek_bar);
            txtTv_option =   itemView.findViewById(R.id.tv_option);
            txtTv_percent =   itemView.findViewById(R.id.tv_percent);
            AnwLayout =  itemView.findViewById(R.id.answLayout);
          //  AnwLayout.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {

            ((FragmentActivity)context).getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("null")
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;

    }




}
