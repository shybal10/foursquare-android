package com.example.shybal.projectapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class AddReviewActivity extends AppCompatActivity {

    private int count = 0;


    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);



        Button b1 = (Button)findViewById(R.id.photo_food_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        Button submitButton = (Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            count = count+1;
            if(count == 1) {
                ImageView imageView = (ImageView) findViewById(R.id.photo_food1);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                imageView.setVisibility(View.VISIBLE);
            }
            if(count == 2) {
                ImageView imageView = (ImageView) findViewById(R.id.photo_food2);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                imageView.setVisibility(View.VISIBLE);
            }
            if(count == 3) {
                ImageView imageView = (ImageView) findViewById(R.id.photo_food3);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                imageView.setVisibility(View.VISIBLE);
            }
            else if (count > 3){
                Toast.makeText(this, "Sorry cannot add anymore pictures",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
      //  AddReviewActivity.this.finish();
        super.onBackPressed();
    }

    public void returnToParent(View view){
        //Intent hotelDetailIntent = new Intent(AddReviewActivity.this, HotelDetailActivity.class);
        //AddReviewActivity.this.finish();
        //startActivity(hotelDetailIntent);
        onBackPressed();
    }
}
