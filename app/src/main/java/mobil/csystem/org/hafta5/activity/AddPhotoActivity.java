package mobil.csystem.org.hafta5.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import mobil.csystem.org.hafta5.R;

public class AddPhotoActivity extends AppCompatActivity {

    ImageView Add_photo;
    Button btn_choose_photo;
    Button btn_save_photo;

    FirebaseStorage firebaseStorage;
    FirebaseAuth mAuth;

    Uri filePath;
    private StorageReference mStorageRef;
    private ProgressDialog progressDialog;
    private static final int IMAGE_REQUEST = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        Add_photo = findViewById(R.id.add_PhotoView);
        btn_choose_photo = findViewById(R.id.btn_choosePhoto);
        btn_save_photo = findViewById(R.id.btn_savePhoto);

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        showPhoto();



        btn_choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoosePhoto();
            }
        });

        btn_save_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavePhoto();
            }
        });


    }
    private void showPhoto(){
        showProgressDialog();
        StorageReference storageRef = firebaseStorage.getReference();
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(AddPhotoActivity.this).load(uri).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Add_photo.setImageBitmap(resource);
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AddPhotoActivity.this, "Fotoğraf Yükleme işlemi başarısız", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ChoosePhoto(){

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Resim Seçiniz"),IMAGE_REQUEST);

    }
    private void SavePhoto(){
        if (filePath != null) {
            showProgressDialog();
            StorageReference storageRef = firebaseStorage.getReference();
            storageRef.child("userprofilephoto").putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    dismissProgressDialog();
                    Toast.makeText(AddPhotoActivity.this, "Fotoğraf başarılı bir şekilde kaydedildi.", Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    dismissProgressDialog();
                    Toast.makeText(AddPhotoActivity.this, "Fotoğraf Kaydedilemedi", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(AddPhotoActivity.this);
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    private void  dismissProgressDialog(){
        progressDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Picasso.with(AddPhotoActivity.this).load(filePath).fit().centerCrop().into(Add_photo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
