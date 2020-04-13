package com.jabari.client.activity.account;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.client.R;
import com.jabari.client.activity.help.SupActivity;
import com.jabari.client.activity.main.FirstActivity;
import com.jabari.client.activity.main.MainActivity;
import com.jabari.client.controller.UserController;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.custom.PrefManager;
import com.jabari.client.network.config.ApiInterface;
import com.jabari.client.network.model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class ProfileActivity extends AppCompatActivity {
    private TextView tv_mobile;
    private EditText et_name, et_email;
    private ImageView img_profile;
    private int GALLERY = 1, CAMERA = 2;
    private Uri imageUri;
    private String avatar;
    private static final String IMAGE_DIRECTORY = "/demonuts";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setView();
    }

    public void OnExitClick(View view) {
        GlobalVariables.isLogin = false;
        removePreferences();
        startActivity(new Intent(ProfileActivity.this, FirstActivity.class));

    }

    private void removePreferences() {

        PrefManager prefManager = new PrefManager(this);
        prefManager.removeToken();
        prefManager.removeUser();
        GlobalVariables.tok = "";
        GlobalVariables.phone = "";

    }

    private void setView() {
        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        tv_mobile = findViewById(R.id.tv_phoneNum);
        img_profile = findViewById(R.id.iv_pro);
        et_email.setText(GlobalVariables.mail);
        tv_mobile.setText(GlobalVariables.phone);
        String fullName = GlobalVariables.firstName + " " + GlobalVariables.lastName;
        et_name.setText(fullName);
        et_name.setEnabled(true);
        et_email.setEnabled(true);
    }

    public void OnBack(View view) {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }

    public void setEditable(View view) {
        et_name.setEnabled(true);
        et_email.setEnabled(true);
    }

    public void OnSupportClicked(View view) {
        startActivity(new Intent(ProfileActivity.this, SupActivity.class));
    }

    public void showPictureDialog(View view) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(getResources().getString(R.string.select_action));
        String[] pictureDialogItems = {
                getResources().getString(R.string.select_photo),
                getResources().getString(R.string.capture_photo)};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }

                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    public void takePhotoFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                final Uri contentURI = data.getData();

                try {

                    final Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle(getResources().getString(R.string.save_photo));
                    dialog.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            img_profile.setImageBitmap(bitmap);
                            saveImage(encodeTobase64(bitmap));
                            upload(getRealPathFromURI(contentURI));


                        }
                    });

                    dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {


            try {
                final Bitmap thumbnail;
                thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(getResources().getString(R.string.save_photo));
                dialog.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        img_profile.setImageBitmap(thumbnail);
                        saveImage(encodeTobase64(thumbnail));
                        upload(saveInGallery(thumbnail));


                    }
                });

                dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    private void saveImage(String bitmap) {

        PrefManager prefManager = new PrefManager(getBaseContext());
        prefManager.setBitmapString(bitmap);

    }

    public void upload(String photoPath) {
        ApiInterface.uploadImageCallback uploadImageCallback = new ApiInterface.uploadImageCallback() {
            @Override
            public void onResponse(String url) {
                avatar = url;
            }

            @Override
            public void onFailure(String error) {

            }
        };
        UserController userController = new UserController(uploadImageCallback);
        userController.upload(photoPath);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    private String saveInGallery(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());


            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    private User editedInfo() {
        User user = new User();
        user.setEmail(et_email.getText().toString());
        PrefManager prefManager = new PrefManager(getBaseContext());
        user.setAvatar(avatar);
        user.setFirstName(et_name.getText().toString());
        return user;
    }

    public void updateUserClicked(View view) {

        ApiInterface.updateUserCallback updateUserCallback = new ApiInterface.updateUserCallback() {
            @Override
            public void onResponse(boolean success) {
                if (success)
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                else
                    Toasty.error(ProfileActivity.this, "درخواست با خطا مواجه شد", Toasty.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String error) {
                if (error.equals("connection"))
                    Toasty.error(ProfileActivity.this, "خطا در برقراری ارتباط!", Toasty.LENGTH_LONG).show();

            }
        };
        UserController userController = new UserController(updateUserCallback);
        userController.updateUser(editedInfo());
    }

}
