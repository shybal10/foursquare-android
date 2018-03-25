

package com.example.shybal.projectapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.facebook.FacebookSdk.getApplicationContext;

public class RatingActivity extends Dialog implements android.view.View.OnClickListener {


    String ratingValue;
    String ratingColor;
    TextView ratingTextView;
    SimpleRatingBar ratingBar;
    Button submitButton;
    ImageButton closeButton;

    public RatingActivity(@NonNull Context context,String rating,String color) {
        super(context);
        ratingValue = rating;
        ratingColor = color;



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ratingTextView = (TextView) findViewById(R.id.rating_textview_id);

        if(ratingValue.equals("NA")){
            ratingTextView.setText("NA");
        }
        else {

            ratingTextView.setText(String.format("%.1f",Float.valueOf(ratingValue)/2));
        }

        ratingTextView.setTextColor(Color.parseColor(ratingColor));
        addListenerOnButtonClick();
    }

    public void addListenerOnButtonClick(){
        closeButton = (ImageButton) findViewById(R.id.imageView_close);
        ratingBar=(SimpleRatingBar) findViewById(R.id.hotel_rating_bar);

        submitButton=(Button)findViewById(R.id.submit_button);
        //Performing action on Button Click
        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                String rating=String.valueOf(ratingBar.getRating());
                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
                dismiss();
            }

        });

        closeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
    @Override
    public void onClick(View view) {

    }

}

