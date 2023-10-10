package com.example.gplx_b2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gplx_b2.Controller.AnswerAdapter;
import com.example.gplx_b2.DAO.QuestionDAO;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.myInterface.IClickAnswerItemListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    Context context = this;
    QuestionDAO questionDAO;
    ImageView imgQuestionTopic;
    TextView tvNumber;
    ViewPager2 viewPager2;
//    RecyclerView answerRecyclerView;
//    private AnswerAdapter answerAdapter;
//    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        InitUI();
        Topic topic = GetData();
        RenderQuestionPager(topic);
//        RenderQuestion(topic);
    }

    private void InitUI() {
        imgQuestionTopic = findViewById(R.id.imgQuestionTopic);

        tvNumber = findViewById(R.id.txtNumber);
//        tvQuestion= findViewById(R.id.txtQuestion);
//        answerRecyclerView = findViewById(R.id.rcvAnswer);
        viewPager2 = findViewById(R.id.vpgQuestion);
    }

    private Topic GetData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return null;
        }
        Topic topic = (Topic) bundle.get("object_topic");

        return topic;
    }

    private List<Question> GetListQuestion() {
        questionDAO = new QuestionDAO();
        List<Question> questionList = questionDAO.getListQuestions();

        return questionList;
    }

    private void RenderQuestionPager(Topic topic) {
        String name = topic.getImage();
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        imgQuestionTopic.setImageResource(imageResource);

        List<Question> questionList = GetListQuestion();
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
//                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, questionList);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, questionList);
        viewPager2.setAdapter(viewPagerAdapter);
    }

//    private void RenderQuestion(Topic topic) {
//        List<Question> questionList = GetListQuestion();
//        Question question = questionList.get(index);
//
//        String name = topic.getImage();
//        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
//
//        imgQuestionTopic.setImageResource(imageResource);
////        tvQuestion.setText(question.getQuestion());
//
//        List<String> answerList = question.getAnswerList();
////        RenderListAnswer(answerList);
//    }

//    private void RenderListAnswer(List<String> list) {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        answerRecyclerView.setLayoutManager(linearLayoutManager);
//
//        answerAdapter = new AnswerAdapter(this, StandardListAnswer(list), new IClickAnswerItemListener() {
//            @Override
//            public void onClickCheckBox(ArrayList<Integer> selectCheck, int adapterPosition) {
//                for (int i = 0; i < selectCheck.size(); i++) {
//                    if (i == adapterPosition) {
//                        selectCheck.set(i, 1);
//                    } else {
//                        selectCheck.set(i, 0);
//                    }
//                }
//            }
//
//            @Override
//            public boolean onCheckedChange(String answer, boolean isChecked, int adapterPosition) {
//                boolean isCorrect = false;
//                if (isChecked) {
//                    List<Question> questionList = GetListQuestion();
//                    Question question = questionList.get(index);
//                    String answerCorrect = question.getAnswerCorrect();
//
//                    if (answer.equals(answerCorrect))
//                        isCorrect = true;
//                }
//
//                return isCorrect;
//            }
//        });
//        answerRecyclerView.setAdapter(answerAdapter);
//    }

//    private List<String> StandardListAnswer(List<String> list) {
//        List<String> newAnswerList = new ArrayList<>();
//
//        for (String answer : list)
//            if (answer != null)
//                newAnswerList.add(answer);
//
//        return newAnswerList;
//    }

//    private void ChangeQuestion(String type) {
//        Topic topic = GetData();
//        int length = GetListQuestion().size();
//
//        switch (type) {
//            case "Previous":
//                index -= 1;
//
//                if (index < 0)
//                    index = 0;
//                else {
//                    RenderQuestion(topic);
//                }
//                break;
//            case "Next":
//                index += 1;
//
//                if (index == length)
//                    index = length - 1;
//                else {
//                    RenderQuestion(topic);
//                }
//                break;
//            default:
//                RenderQuestion(topic);
//        }
//    }

//    public void onClickPrev(View view) {
//        ChangeQuestion("Previous");
//    }
//
//    public void onClickNext(View view) {
//        ChangeQuestion("Next");
//    }

    public void onClickBackToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}