package com.samyotech.fabcustomer.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cocosw.bottomsheet.BottomSheet;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samyotech.fabcustomer.DTO.CategoryDTO;
import com.samyotech.fabcustomer.DTO.PostedJobDTO;
import com.samyotech.fabcustomer.DTO.UserDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.https.HttpsRequest;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.interfacess.Helper;
import com.samyotech.fabcustomer.interfacess.OnSpinerItemClick;
import com.samyotech.fabcustomer.network.NetworkManager;
import com.samyotech.fabcustomer.preferences.SharedPrefrence;
import com.samyotech.fabcustomer.utils.CustomEditText;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.ImageCompression;
import com.samyotech.fabcustomer.utils.MainFragment;
import com.samyotech.fabcustomer.utils.ProjectUtils;
import com.samyotech.fabcustomer.utils.SpinnerDialog;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class EditJob extends AppCompatActivity implements View.OnClickListener {
    private String TAG = EditJob.class.getSimpleName();
    private Context mContext;
    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    private ArrayList<CategoryDTO> categoryDTOS;
    private HashMap<String, String> parmsadd = new HashMap<>();
    private HashMap<String, String> parmsCategory = new HashMap<>();
    private ImageView ivImg, ivBack;
    private CustomEditText etCommet, etTitle, etAddress;
    private LinearLayout llPicture, llPost;
    private CustomTextView tvCategory;
    Uri picUri;
    int PICK_FROM_CAMERA = 1, PICK_FROM_GALLERY = 2;
    int CROP_CAMERA_IMAGE = 3, CROP_GALLERY_IMAGE = 4;
    BottomSheet.Builder builder;
    String pathOfImage = "";
    Bitmap bm;
    ImageCompression imageCompression;
    private File image;
    public static final int MEDIA_TYPE_VIDEO = 6;
    HashMap<String, File> parmsFile = new HashMap<>();
    private Place place;
    private SpinnerDialog spinnerDialogCate;
    private PostedJobDTO postedJobDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);
        mContext = EditJob.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        userDTO = prefrence.getParentUser(Consts.USER_DTO);
        parmsadd.put(Consts.USER_ID, userDTO.getUser_id());
        parmsCategory.put(Consts.USER_ID, userDTO.getUser_id());

        if (getIntent().hasExtra(Consts.POST_JOB_DTO)) {
            postedJobDTO = (PostedJobDTO) getIntent().getSerializableExtra(Consts.POST_JOB_DTO);
        }
        setUiAction();
    }


    public void setUiAction() {
        ivBack = findViewById(R.id.ivBack);
        tvCategory = findViewById(R.id.tvCategory);
        ivImg = findViewById(R.id.ivImg);

        ivBack.setOnClickListener(this);
        etTitle = findViewById(R.id.etTitle);
        etCommet = findViewById(R.id.etCommet);
        etAddress = findViewById(R.id.etAddress);
        llPicture = findViewById(R.id.llPicture);
        llPost = findViewById(R.id.llPost);

        tvCategory.setOnClickListener(this);
        etAddress.setOnClickListener(this);
        llPicture.setOnClickListener(this);
        llPost.setOnClickListener(this);
        builder = new BottomSheet.Builder(EditJob.this).sheet(R.menu.menu_cards);
        builder.title(getResources().getString(R.string.take_image));
        builder.listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case R.id.camera_cards:
                        if (ProjectUtils.hasPermissionInManifest(EditJob.this, PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                            if (ProjectUtils.hasPermissionInManifest(EditJob.this, PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                try {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                    File file = getOutputMediaFile(1);
                                    if (!file.exists()) {
                                        try {
                                            file.createNewFile();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        //Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.asd", newFile);
                                        picUri = FileProvider.getUriForFile(EditJob.this.getApplicationContext(), EditJob.this.getApplicationContext().getPackageName() + ".fileprovider", file);
                                    } else {
                                        picUri = Uri.fromFile(file); // create
                                    }


                                    prefrence.setValue(Consts.IMAGE_URI_CAMERA, picUri.toString());
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file
                                    startActivityForResult(intent, PICK_FROM_CAMERA);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        break;
                    case R.id.gallery_cards:
                        if (ProjectUtils.hasPermissionInManifest(EditJob.this, PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                            if (ProjectUtils.hasPermissionInManifest(EditJob.this, PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                File file = getOutputMediaFile(1);
                                if (!file.exists()) {
                                    try {
                                        file.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                picUri = Uri.fromFile(file);

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), PICK_FROM_GALLERY);

                            }
                        }
                        break;
                    case R.id.cancel_cards:
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        });
                        break;
                }
            }
        });

        if (NetworkManager.isConnectToInternet(mContext)) {
            getCategory();
        } else {
            ProjectUtils.showLong(mContext, getResources().getString(R.string.internet_concation));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llPicture:
                builder.show();
                break;
            case R.id.llPost:
                submitForm();
                break;
            case R.id.etAddress:
                findPlace();
                break;
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvCategory:
                spinnerDialogCate.showSpinerDialog();
                break;
        }
    }

    public void submitForm() {
        if (!validateTitle()) {
            return;
        } else if (!validateAddress()) {
            return;
        } else if (!validateComment()) {
            return;
        } else {
            if (NetworkManager.isConnectToInternet(mContext)) {
                addPost();

            } else {
                ProjectUtils.showLong(mContext, getResources().getString(R.string.internet_concation));
            }
        }
    }


    private File getOutputMediaFile(int type) {
        String root = Environment.getExternalStorageDirectory().toString();

        File mediaStorageDir = new File(root, Consts.APP_NAME);

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    Consts.APP_NAME + timeStamp + ".png");

        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CROP_CAMERA_IMAGE) {

            if (data != null) {
                picUri = Uri.parse(data.getExtras().getString("resultUri"));

                try {
                    //bitmap = MediaStore.Images.Media.getBitmap(PostJob.this.getContentResolver(), resultUri);
                    pathOfImage = picUri.getPath();
                    imageCompression = new ImageCompression(EditJob.this);
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                            Glide.with(mContext).load("file://" + imagePath)
                                    .thumbnail(0.5f)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivImg);

                            pathOfImage = imagePath;
                            image = new File(imagePath);
                            parmsFile.put(Consts.AVTAR, image);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        if (requestCode == CROP_GALLERY_IMAGE) {

            if (data != null) {
                picUri = Uri.parse(data.getExtras().getString("resultUri"));
                Log.e("image 1", picUri + "");
                try {
                    bm = MediaStore.Images.Media.getBitmap(EditJob.this.getContentResolver(), picUri);
                    pathOfImage = picUri.getPath();
                    imageCompression = new ImageCompression(EditJob.this);
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {

                            Glide.with(mContext).load("file://" + imagePath)
                                    .thumbnail(0.5f)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivImg);
                            image = new File(imagePath);

                            pathOfImage = imagePath;
                            parmsFile.put(Consts.AVTAR, image);

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        if (requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK) {
            if (picUri != null) {

                picUri = Uri.parse(prefrence.getValue(Consts.IMAGE_URI_CAMERA));
                // image = new File(ConvertUriToFilePath.getPathFromURI(PostJob.this, picUri));
                startCropping(picUri, CROP_CAMERA_IMAGE);
            } else {
                picUri = Uri.parse(prefrence.getValue(Consts.IMAGE_URI_CAMERA));
                // image = new File(ConvertUriToFilePath.getPathFromURI(PostJob.this, picUri));

                startCropping(picUri, CROP_CAMERA_IMAGE);
            }
        }


        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            try {
                Uri tempUri = data.getData();

                Log.e("front tempUri", "" + tempUri);
                if (tempUri != null) {
                    //    image = new File(ConvertUriToFilePath.getPathFromURI(PostJob.this, tempUri));
                    Log.e("image 2", image + "");
                    startCropping(tempUri, CROP_GALLERY_IMAGE);
                } else {

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                place = PlaceAutocomplete.getPlace(this, data);
                getAddress(place.getLatLng().latitude, place.getLatLng().longitude);


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }


    public void startCropping(Uri uri, int requestCode) {

        Intent intent = new Intent(EditJob.this, MainFragment.class);
        intent.putExtra("imageUri", uri.toString());
        intent.putExtra("requestCode", requestCode);
        startActivityForResult(intent, requestCode);
    }


    public boolean validateComment() {
        if (!ProjectUtils.isEditTextFilled(etCommet)) {
            etCommet.setError(getResources().getString(R.string.val_des));
            etCommet.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateAddress() {
        if (!ProjectUtils.isEditTextFilled(etAddress)) {
            etAddress.setError(getResources().getString(R.string.val_address));
            etAddress.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateTitle() {
        if (!ProjectUtils.isEditTextFilled(etTitle)) {
            etTitle.setError(getResources().getString(R.string.val_title));
            etTitle.requestFocus();
            return false;
        } else {
            return true;
        }
    }


    public void addPost() {
        parmsadd.put(Consts.JOB_ID, postedJobDTO.getJob_id());
        parmsadd.put(Consts.TITLE, ProjectUtils.getEditTextValue(etTitle));
        parmsadd.put(Consts.DESCRIPTION, ProjectUtils.getEditTextValue(etCommet));
        parmsadd.put(Consts.ADDRESS, ProjectUtils.getEditTextValue(etAddress));
        ProjectUtils.showProgressDialog(mContext, false,  getResources().getString(R.string.please_wait));

        new HttpsRequest(Consts.EDIT_POST_JOB_API, parmsadd, parmsFile, mContext).imagePost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    ProjectUtils.showLong(mContext, msg);
                    finish();
                } else {
                    ProjectUtils.showLong(mContext, msg);
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    public void findPlace() {
        try {
          /*  Intent intent = new PlaceAutocomplete
                    .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(this);
            startActivityForResult(intent, 101);*/

            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            startActivityForResult(builder.build(this), 101);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }


    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();
            Log.e("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);

            etAddress.setText(obj.getAddressLine(0));

            parmsadd.put(Consts.LATI, String.valueOf(lat));
            parmsadd.put(Consts.LONGI, String.valueOf(lng));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void getCategory() {
        new HttpsRequest(Consts.GET_ALL_CATEGORY_API, parmsCategory, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        categoryDTOS = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<CategoryDTO>>() {
                        }.getType();
                        categoryDTOS = (ArrayList<CategoryDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), getpetDTO);

                        showData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }


    public void showData() {
        etCommet.setText(postedJobDTO.getDescription());
        etTitle.setText(postedJobDTO.getTitle());
        etAddress.setText(postedJobDTO.getAddress());

        for (int j = 0; j < categoryDTOS.size(); j++) {
            if (categoryDTOS.get(j).getId().equalsIgnoreCase(postedJobDTO.getCategory_id())) {
                categoryDTOS.get(j).setSelected(true);
                tvCategory.setText(categoryDTOS.get(j).getCat_name());
            }
        }
        spinnerDialogCate = new SpinnerDialog((Activity) mContext, categoryDTOS, getResources().getString(R.string.select_category));// With 	Animation
        spinnerDialogCate.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                tvCategory.setText(item);
                parmsadd.put(Consts.CATEGORY_ID, id);
            }
        });

        Glide.with(mContext).load(postedJobDTO.getAvtar())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImg);

    }
}
