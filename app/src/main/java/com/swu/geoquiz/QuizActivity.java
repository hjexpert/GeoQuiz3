package com.swu.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mCheatButton;


    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_ocean, false),
            new Question(R.string.question_mideast, true),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_america, true),
            new Question(R.string.question_asia, false)
    };

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private int mCurrentIndex = 0;
    private boolean mIsCheater;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);


        mQuestionTextView.setOnClickListener(
               new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
                    updateQuestion();
                }
        });

        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTextView.setText(question);

        mTrueButton =(Button) findViewById(R.id.true_button);


        mTrueButton =(Button) findViewById(R.id.true_button);

        if(mQuestionBank[mCurrentIndex].isSloved()==true) {
            mTrueButton.setEnabled(false);

        }

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }

        });

        mFalseButton =(Button) findViewById(R.id.false_button);

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }

        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);

        mCheatButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Start CheatActivity
//                Intent intent = new Intent(QuizActivity.this,CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this,answerIsTrue);

                startActivity(intent);

            }
        });





        mNextButton =(ImageButton) findViewById(R.id.next_button);

        mNextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
                        mIsCheater = false;
                        updateQuestion();
            }
        });

        mPrevButton =(ImageButton) findViewById(R.id.prev_button);

        mPrevButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        if(mCurrentIndex== 0)
                            mCurrentIndex = mQuestionBank.length-1;
                        else
                            mCurrentIndex = mCurrentIndex-1;
                        updateQuestion();
                    }
                });



        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);


    }



    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    private void updateQuestion() {

        //Log.d(TAG, "Updating question text", new Exception());
        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTextView.setText(question);
        if(mQuestionBank[mCurrentIndex].isSloved()==true) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }
        else{
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }

    }

    private void checkAnswer(boolean userpressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        int intNumofSolved = 0;
        int intNumofTried = 0;


        mQuestionBank[mCurrentIndex].mTried();

        if(mIsCheater){
            messageResId = R.string.judgement_toast;
        }
        else {
            if(userpressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mQuestionBank[mCurrentIndex].Sloved();
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();

        if(mCurrentIndex == (mQuestionBank.length-1)){
            for(int i = 0;i<mQuestionBank.length;i++){
                if(mQuestionBank[i].isSloved()) {
                    intNumofSolved++;
                    intNumofTried++;
                }
                else
                    intNumofTried++;
            }
            float score =  (float) intNumofSolved/(float) intNumofTried;

            String strScore = String.valueOf(score);

            Toast.makeText(getApplicationContext(), strScore, Toast.LENGTH_LONG).show();
        }


    }
}
