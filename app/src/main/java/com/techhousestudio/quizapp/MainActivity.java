package com.techhousestudio.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickStartQuizButton(View view) {
        startActivityForResult(new Intent(MainActivity.this, QuizActivity.class), 192);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 192) {
            int mark = data.getIntExtra("mark",0);
            HintBottomSheetFragment hintBottomSheetFragment = new HintBottomSheetFragment();
            Bundle b = new Bundle();
            b.putString("hint", mark+"");
            hintBottomSheetFragment.setArguments(b);
            hintBottomSheetFragment.show(getSupportFragmentManager(), "hint-bottom_sheet");
        }
    }
}
