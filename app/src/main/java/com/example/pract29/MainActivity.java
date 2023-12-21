package com.example.pract29;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GestureDetector.OnGestureListener {

    private Animation left_in,left_out,right_in,right_out;
    GestureDetector detector;
    private ViewFlipper viewFlipper;
    final int CAMERA_REQUEST = 1;
    private Uri picUri;
    Button btnWeb, btnMap, btnCall, btnPhoto, btnNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Съёмка и Кадрирование");
        btnWeb = findViewById(R.id.btnWeb);
        btnMap = findViewById(R.id.btnMap);
        btnCall = findViewById(R.id.btnCall);
        btnPhoto = findViewById(R.id.btnPhoto);
        btnNotes = findViewById(R.id.btnNotes);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);


        btnWeb.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        btnNotes.setOnClickListener(this);


        detector = new GestureDetector(this);

        left_in = AnimationUtils.loadAnimation(this, R.anim.left_in);
        left_out = AnimationUtils.loadAnimation(this, R.anim.left_out);
        right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
        right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);

        start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return this.detector.onTouchEvent(event);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {

        Intent intent;
        if (v.getId() == R.id.btnPhoto) {
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(captureIntent, CAMERA_REQUEST);
        } else if (v.getId() == R.id.btnNotes) {
            Intent intents = new Intent(Intent.ACTION_MAIN);
            intents.addCategory(Intent.CATEGORY_APP_CALENDAR);
            startActivity(intents);
        } else if (v.getId() == R.id.btnWeb) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://itch.io"));
            startActivity(intent);
        } else if (v.getId() == R.id.btnMap) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:54.99070374008072, -2.5753297586830204"));
            startActivity(intent);
        } else if (v.getId() == R.id.btnCall) {
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:89247741739"));
            startActivity(intent);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == CAMERA_REQUEST) {

                picUri = data.getData();
            }
            Bundle extras = data.getExtras();

            Bitmap thePic = extras.getParcelable("data");

            ImageView picView = new ImageView(this);
            picView.setImageBitmap(thePic);

            viewFlipper.addView(picView);
            viewFlipper.showNext();

            MediaStore.Images.Media.insertImage(getContentResolver(), thePic, "Название изображения", "Описание изображения");

        }
    }

    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(@Nullable MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        if(motionEvent1.getX()-motionEvent.getX()<-120)
        {
            viewFlipper.setInAnimation(left_in);
            viewFlipper.setOutAnimation(left_out);
            viewFlipper.showNext();

            return true;
        }
        else if(motionEvent1.getX()-motionEvent.getX()>120){
            viewFlipper.setInAnimation(right_in);
            viewFlipper.setOutAnimation(right_out);
            viewFlipper.showPrevious();

            return true;
        }
        return false;
    }
    public void start()
    {
        viewFlipper.setInAnimation(left_in);
        viewFlipper.setOutAnimation(left_out);
    }


}