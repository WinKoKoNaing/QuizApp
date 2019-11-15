package com.techhousestudio.quizapp;

public class Quiz {
    public int ques_id;
    public String ques_name;
    public String ques_hint;
    public String ques_ans;
    public String ques_option1;
    public String ques_option2;
    public String ques_option3;
    public String ques_option4;

    public Quiz(int ques_id, String ques_name, String ques_hint, String ques_ans, String ques_option1, String ques_option2, String ques_option3, String ques_option4) {
        this.ques_id = ques_id;
        this.ques_name = ques_name;
        this.ques_hint = ques_hint;
        this.ques_ans = ques_ans;
        this.ques_option1 = ques_option1;
        this.ques_option2 = ques_option2;
        this.ques_option3 = ques_option3;
        this.ques_option4 = ques_option4;
    }



}
