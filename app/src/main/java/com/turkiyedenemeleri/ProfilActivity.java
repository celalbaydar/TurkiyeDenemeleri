package com.turkiyedenemeleri;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.base.BaseActivity;
import com.turkiyedenemeleri.customviews.TDEditText;
import com.turkiyedenemeleri.presenter.ProfilPresenter;
import com.turkiyedenemeleri.presenter.contract.ProfileContract;
import com.turkiyedenemeleri.util.ActivityUtil;
import com.turkiyedenemeleri.util.FileUtil;
import com.turkiyedenemeleri.util.PicassoUtil;
import com.turkiyedenemeleri.util.ProgressDialogUtil;
import com.turkiyedenemeleri.util.SharedPreferenceUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfilActivity extends BaseActivity<ProfilPresenter> implements ProfileContract.View {

    private Uri mCropImageUri;
    @BindView(R.id.profile_image)
    CircleImageView profilePhoto;
    @BindView(R.id.profil_adi)
    TDEditText profilAdi;
    @BindView(R.id.radioSex)
    RadioGroup cinsiyet;
    @BindView(R.id.rb_erkek)
    RadioButton erkek;
    @BindView(R.id.rb_kadın)
    RadioButton kadın;
    @BindView(R.id.spinner)
    Spinner il;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setInitialValues() {
        mPresenter = new ProfilPresenter();
        profilAdi.setText(MyApp.loggedUser.getKullaniciadi());

        if (MyApp.loggedUser.getCinsiyet() == 1) {
            cinsiyet.check(R.id.rb_kadın);
        } else if (MyApp.loggedUser.getCinsiyet() == 2) {
            cinsiyet.check(R.id.rb_erkek);
        }
        il.setSelection(MyApp.loggedUser.getIl(), true);

        new PicassoUtil(this).loadImageWithCache(MyApp.loggedUser.getResim_url(), profilePhoto);
    }

    @Override
    protected void initViews() {


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_profil;
    }

    @OnClick(R.id.profile_image)
    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }

    @OnClick(R.id.btnRegister)
    public void onClick(View view) {
        String strIl = String.valueOf(il.getSelectedItemPosition());
        String token = MyApp.loggedUserId;
        String strCinsiyet = (cinsiyet.getCheckedRadioButtonId() == R.id.rb_erkek) ? "2" : "1";
        String kullaniciAdi = profilAdi.getText().toString();
        FileUtil fileUtil = new FileUtil();



        if (mCropImageUri != null) {
            java.net.URI juri = fileUtil.getUriFromAndroidUri(mCropImageUri.toString());
            String path = fileUtil.getRealPathFromUri(this, mCropImageUri);
            File file = new File(path);
            Log.e("TAG1",""+Integer.parseInt(String.valueOf(file.length()/1024)));


            File compressedImageFile = Compressor.getDefault(this).compressToFile(file);
            Log.e("TAG2",""+Integer.parseInt(String.valueOf(compressedImageFile.length()/1024)));


            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), compressedImageFile);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", compressedImageFile.getName(), requestFile);

            Map<String, String> map = new HashMap<>();
            map.put("il", strIl);
            map.put("token", token);
            map.put("cinsiyet", strCinsiyet);
            map.put("kullaniciadi", kullaniciAdi);
            mPresenter.updateImage(map, body);





            } else mPresenter.updateImage(strIl, token, strCinsiyet, kullaniciAdi);

        dialog= new ProgressDialogUtil(mContext).create("Kaydediliyor");

    }




    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                mCropImageUri = imageUri;
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                startCropImageActivity(imageUri);
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                mCropImageUri = resultUri;
                profilePhoto.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        Intent intent = CropImage.activity(imageUri)
                .getIntent(this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void profileUpdated() {
        if (dialog!=null) dialog.dismiss();
        SharedPreferenceUtil.setUserFillProfil(true);
        Toast.makeText(mContext, "Profil güncellendi", Toast.LENGTH_SHORT).show();
        int flags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK;
        ActivityUtil.startActivity(mContext, MainActivity.class, flags);
    }
}
