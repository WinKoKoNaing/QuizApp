package com.techhousestudio.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class QuizActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvQuestionName)
    TextView tvQuestionName;
    @BindView(R.id.btnOption1)
    MaterialButton btnOption1;
    @BindView(R.id.btnOption2)
    MaterialButton btnOption2;
    @BindView(R.id.btnOption3)
    MaterialButton btnOption3;
    @BindView(R.id.btnOption4)
    MaterialButton btnOption4;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottom_navigation;
    @BindView(R.id.tvQuestionCount)
    TextView tvQuestionCount;
    @BindView(R.id.tvTimer)
    TextView tvTimer;


    private List<Quiz> generatedQuiz = new ArrayList<>();
    private int index = 0;
    private int currentIndex = 0;
    private int totalMark = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        generatedQuiz.addAll(generateQuiz());

        tvQuestionCount.setText((index + 1) + "/" + 10);

        updateQuiz(index);


        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // long minutes = (milliseconds / 1000) / 60;
                long minutes = (millisUntilFinished / 1000) / 60;
                // long seconds = (milliseconds / 1000);
                long seconds = (millisUntilFinished / 1000) % 60;
                tvTimer.setText(minutes + ":" + seconds);
            }

            @Override
            public void onFinish() {
                tvTimer.setText("Finished");
                Intent intent = new Intent();
                intent.putExtra("mark", totalMark);
                setResult(RESULT_OK, intent);
                finish();
            }
        }.start();


        bottom_navigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_previous:

                    Quiz quiz = generatedQuiz.get(index);

                    if (btnOption1.getText().toString().equals(quiz.ques_ans)) {
                        btnOption1.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                    } else if (btnOption2.getText().toString().equals(quiz.ques_ans)) {
                        btnOption2.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                    } else if (btnOption3.getText().toString().equals(quiz.ques_ans)) {
                        btnOption3.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                    } else if (btnOption4.getText().toString().equals(quiz.ques_ans)) {
                        btnOption4.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                    }


                    currentIndex = index;

                    btnOption1.setEnabled(false);
                    btnOption2.setEnabled(false);
                    btnOption3.setEnabled(false);
                    btnOption4.setEnabled(false);
                    if (index > 0) {
                        index--;
                        updateQuiz(index);

                        tvQuestionCount.setText((index + 1) + "/" + 10);
                    } else {
                        Toast.makeText(QuizActivity.this, "No previous quiz", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.action_hint:
                    HintBottomSheetFragment hintBottomSheetFragment = new HintBottomSheetFragment();
                    Bundle b = new Bundle();
                    b.putString("hint", generatedQuiz.get(index).ques_hint);
                    hintBottomSheetFragment.setArguments(b);
                    hintBottomSheetFragment.show(getSupportFragmentManager(), "hint-bottom_sheet");
                    break;
                case R.id.action_next:


                    if (currentIndex <= index) {
                        btnOption1.setEnabled(true);
                        btnOption2.setEnabled(true);
                        btnOption3.setEnabled(true);
                        btnOption4.setEnabled(true);
                    }

                    btnOption1.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

                    btnOption2.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

                    btnOption3.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

                    btnOption4.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);


                    if (index == generateQuiz().size() - 1) {
                        Intent intent = new Intent();
                        intent.putExtra("mark", totalMark);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        index++;
                        updateQuiz(index);
                        tvQuestionCount.setText((index + 1) + "/" + 10);
                    }

                    break;
            }
            return true;
        });


    }

    @OnClick({R.id.btnOption1, R.id.btnOption2, R.id.btnOption3, R.id.btnOption4})
    public void quizOptionButtonClick(View view) {

        Timber.i("Array Index " + generatedQuiz.get(index).ques_ans + "\n" + generatedQuiz.get(index).ques_name);
        Quiz quiz = generatedQuiz.get(index);
        MaterialButton clickButton = ((MaterialButton) view);


        if (clickButton.getText().toString().trim().equals(quiz.ques_ans)) {
            totalMark++;
            clickButton.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
        } else {
            clickButton.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);

            if (btnOption1.getText().toString().equals(quiz.ques_ans)) {
                btnOption1.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
            } else if (btnOption2.getText().toString().equals(quiz.ques_ans)) {
                btnOption2.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
            } else if (btnOption3.getText().toString().equals(quiz.ques_ans)) {
                btnOption3.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                ;
            } else if (btnOption4.getText().toString().equals(quiz.ques_ans)) {
                btnOption4.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
            }

        }
        btnOption1.setEnabled(false);
        btnOption2.setEnabled(false);
        btnOption3.setEnabled(false);
        btnOption4.setEnabled(false);
    }

    private void updateQuiz(int pos) {
        Quiz quiz = generatedQuiz.get(pos);
        tvQuestionName.setText(quiz.ques_name);
        btnOption1.setText(quiz.ques_option1);
        btnOption2.setText(quiz.ques_option2);
        btnOption3.setText(quiz.ques_option3);
        btnOption4.setText(quiz.ques_option4);
    }


    private List<Quiz> generateQuiz() {
        List<Quiz> quizzes = new ArrayList<>();

        List<Quiz> quizList = Arrays.asList(
                new Quiz(1, "One", "haha", "B", "A", "B", "C", "D"),
                new Quiz(1, "Two", "haha", "C", "A", "B", "C", "D"),
                new Quiz(1, "Three", "haha", "A", "A", "B", "C", "D"),
                new Quiz(1, "Four", "haha", "D", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D"),
                new Quiz(1, "One", "haha", "Ok", "A", "B", "C", "D")
        );


        int max = quizList.size() - 1;
        for (int i = 0; i < 10; i++) {
            int pos = (int) (Math.random() * max + 0);
            quizzes.add(quizList.get(pos));
        }

        return quizzes;
    }
}
