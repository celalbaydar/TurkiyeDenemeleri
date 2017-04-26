package com.turkiyedenemeleri;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.base.BaseActivity;
import com.turkiyedenemeleri.customviews.TDEditText;
import com.turkiyedenemeleri.util.PicassoUtil;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void setInitialValues() {
        profilAdi.setText(MyApp.loggedUser.getKullaniciadi());

        if (MyApp.loggedUser.getCinsiyet()==1){
            cinsiyet.check(R.id.rb_kadın);
        }else if(MyApp.loggedUser.getCinsiyet()==2){
            cinsiyet.check(R.id.rb_erkek);
        }
        il.setSelection(MyApp.loggedUser.getIl(),true);

        new PicassoUtil(this).loadImageWithCache(MyApp.loggedUser.getResim_url(),profilePhoto);
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
    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                mCropImageUri = imageUri;
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},   CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                startCropImageActivity(imageUri);
            }
        }else  if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
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
    public void showError(int errorCode,String msg) {

    }

}
