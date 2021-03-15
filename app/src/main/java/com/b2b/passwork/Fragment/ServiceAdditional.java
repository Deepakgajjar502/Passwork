package com.b2b.passwork.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.b2b.passwork.Activity.MainActivity;
import com.b2b.passwork.Model.DefaultResponse;
import com.b2b.passwork.Model.submitTicket.TicketResponse;
import com.b2b.passwork.R;
import com.b2b.passwork.Utility.MediaHelper;
import com.b2b.passwork.Utility.PermissionUtility;
import com.b2b.passwork.Utility.UserSessionManager;
import com.b2b.passwork.interfaces.fragment_position;
import com.b2b.passwork.retrofit.RestManager;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

public class ServiceAdditional extends Fragment implements View.OnClickListener, fragment_position {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.addr_edittext)
    EditText addrEdittext;
    @BindView(R.id.btnNext)
    Button btnNext;
    Boolean btnEnable = true;
    fragment_position position;
    String CategoryId, subCategoryID, fullDate, Time, FloorId, Token, CorporateId, WorkspaceId;
    @BindView(R.id.selectImage)
    LinearLayout selectImage;
    public static final int PICK_IMAGE = 1;
    public static final int REQUEST_CODE_TAKE_PHOTO = 2;
    File photoFile = null;
    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private String TAG = "serviceRequest";
    private Uri mUri;
    String mCurrentPhotoPath;
    private MediaType MEDIA_TYPE;
    String encoded;
    UserSessionManager session;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            position = (fragment_position) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_service_additional, container, false);
        ButterKnife.bind(this, view);
        screenOpen(4);
        btnNext.setOnClickListener(this);
        selectImage.setOnClickListener(this);

        session = new UserSessionManager(getActivity());


        HashMap<String, String> user = session.getUserDetails();
        Token = user.get(UserSessionManager.KEY_ACCESS_TOKEN);
        CorporateId = user.get(UserSessionManager.KEY_COMPANY_ID);

        HashMap<String, String> workspace = session.getworkspaceList();
        WorkspaceId = workspace.get(UserSessionManager.IS_WORKSPACE_ID);


        Bundle arguments = getArguments();
        CategoryId = arguments.getString("categoryId");
        subCategoryID = arguments.getString("subCategory");
        Time = arguments.getString("time");
        fullDate = arguments.getString("date");
        FloorId = arguments.getString("floorId");



        addrEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 0) {
                    btnEnable = false;
                    btnNext.setBackgroundColor(getResources().getColor(R.color.activated_color));
                } else {
                    btnEnable = true;
                    btnNext.setBackgroundColor(getResources().getColor(R.color.accent));
                }
                Log.e("afterTextChanged", editable.length() + "");
            }
        });


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnNext:

                if (btnEnable) {



                    submitTicket(CategoryId, subCategoryID, Time, FloorId,fullDate, Token , CorporateId, WorkspaceId,addrEdittext.getText().toString().trim() ) ;




                } else {


                }


                break;

            case R.id.selectImage:

                boolean result = PermissionUtility.checkPermissionAllPermissions(getActivity());

                if (result) {
                    SelectImage();
                }


                break;


        }
    }

    private void submitTicket(String categoryId, String subCategoryID, String time, String floorId, String fullDate, String Token, String corporateId, String workspaceId, String trim) {


        progressBar.setVisibility(View.VISIBLE);

        String token = "Bearer " + Token;
        Map<String, String> jsonParams = new ArrayMap<>();
//put something inside the map, could be null
        jsonParams.put("corporate_id", corporateId);
        jsonParams.put("workspace_id", workspaceId);
        jsonParams.put("floor_id", floorId);
        jsonParams.put("seat_id", "0");
        jsonParams.put("category_id", categoryId);
        jsonParams.put("sub_category_id", subCategoryID);
        jsonParams.put("description", trim);
        jsonParams.put("date_time", fullDate+" "+ time);
        jsonParams.put("priority", "P1");
        jsonParams.put("image", "data:image/gif;base64,"+encoded);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        Call<TicketResponse> responseBody = RestManager.getInstance().getService()
                .getTicketSave(token, body);
        //"artist",
        responseBody.enqueue(new Callback<TicketResponse>() {
            @Override
            public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                //  RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                progressBar.setVisibility(View.GONE);

                if (response.body() != null) {

                    if (response.isSuccessful()) {

                        TicketResponse response1 = response.body();

                        if (response1.getStatus() == 1) {


                            //StaticUtil.showToast(FragmentManager.ge, "Password update successfully");
                            List<Integer> Ticket = response1.getTicketId();
                            Log.e("ticket", Ticket.get(0)+"");

                            final iOSDialog iOSDialog = new iOSDialog(getContext());
                            iOSDialog.setTitle(getActivity().getString(R.string.app_name));
                            iOSDialog.setSubtitle("The service ticket no."+ Ticket.get(0)+" "+   "has been created successfully.");
                            iOSDialog.setPositiveLabel("OK");
                            //iOSDialog.setNegativeLabel(getActivity().getString(R.string.Lbl_Cancel));
                            iOSDialog.setBoldPositiveLabel(true);

                            iOSDialog.setPositiveListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
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
            public void onFailure(Call<TicketResponse> call, Throwable t) {
                // RotateDialog.newInstance((Activity) getApplicationContext()).stopLoading();
                //  progressBar.setVisibility(View.GONE);
                // StaticUtil.showIOSLikeDialog(getActivity(), "Something went wrong");


            }
        });


    }

    private void SelectImage() {

        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.select_choose);

        TextView tvTitle = (TextView) dialog.findViewById(R.id.title);
        TextView tvLight = (TextView) dialog.findViewById(R.id.labelLight);
        TextView tvDark = (TextView) dialog.findViewById(R.id.label_Dark);
        TextView tvClose = (TextView) dialog.findViewById(R.id.label_close);

        tvTitle.setText("Upload image from");
        tvLight.setText("Camera");
        tvDark.setText("Gallery");


        tvLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission()) {
                    dialog.dismiss();
                    takePhoto();
                } else {
                    requestPermission();
                }
            }
        });

        tvDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);


            }
        });


        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        dialog.show();


    }

    @Override
    public void screenOpen(int screenPossion) {
        position.screenOpen(screenPossion);
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getContext(),
                READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getContext(),
                CAMERA);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new
                String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, CAMERA}, REQUEST_CODE_TAKE_PHOTO);
    }

    private void takePhoto() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {

            if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                try {
                    photoFile = createImageFile();
//                mUri = Uri.fromFile(photoFile);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.i(TAG, "IOException");
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mUri = FileProvider.getUriForFile(getContext(),
                            "com.b2b.passwork.fileprovider",
                            photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                    cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                        List<ResolveInfo> resInfoList = getContext().getPackageManager().queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
                        for (ResolveInfo resolveInfo : resInfoList) {
                            String packageName = resolveInfo.activityInfo.packageName;
                            getContext().grantUriPermission(packageName, mUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        }
                    }
                    startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_PHOTO);
                }
//                startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_PHOTO);
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        /*File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);*/
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d(TAG, "mCurrentPhotoPath " + mCurrentPhotoPath);
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_IMAGE && null != data) {
                Uri selectedImage = data.getData();

                Picasso.get().load(selectedImage)
                        .placeholder(R.drawable.ic_baseline_add_24)
                        .into(imageview);

                mCurrentPhotoPath = MediaHelper.getPath(getContext(), selectedImage);

                photoFile = new File(mCurrentPhotoPath);
                InputStream imageStream = null;
                try {
                    imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    final Bitmap selectedBitmap = BitmapFactory.decodeStream(imageStream);
                    encoded = encodeImage(selectedBitmap);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }



          /*      String imagesPath = data.getStringExtra("data");

                Uri selectedImage = Uri.parse(imagesPath);

                imageview.setImageURI(selectedImage);

                photoFile = new File(imagesPath);*/


            } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {

                MEDIA_TYPE = MediaType.parse("image/*");
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                bitmap = RotateBitmap(bitmap, 90);


                imageview.setImageBitmap(bitmap);


                encoded = encodeImage(bitmap);
                //photoFile.getAbsolutePath();

                Log.e("encode", encoded);
            }


        }


        //TODO: action
    }

    private static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

}