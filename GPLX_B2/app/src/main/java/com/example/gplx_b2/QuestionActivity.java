package com.example.gplx_b2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gplx_b2.Controller.QuestionBottomSheetAdapter;
import com.example.gplx_b2.Controller.TopicAdapter;
import com.example.gplx_b2.Controller.ViewPagerAdapter;
import com.example.gplx_b2.DAO.QuestionDAO;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.myInterface.IClickTopicItemListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    Context context = this;
    QuestionDAO questionDAO;
    ImageView imgQuestionTopic;
    TextView tvNumber, tvPrev, tvNext, tvShowAllQuestion, tvHideQuestionBottomSheet;
    LinearLayout lnBottomSheet;
    ViewPager2 viewPager2;
    BottomSheetBehavior bottomSheetBehavior;
    RecyclerView questionBottomSheetRecyclerView;
    QuestionBottomSheetAdapter questionBottomSheetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        InitUI();
        Topic topic = GetData();
        RenderQuestionPager(topic);

        int length = GetListQuestion().size();
        InitData(1, length);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (position == 0) {
                    tvPrev.setClickable(false);
                } else if (position == length - 1) {
                    tvNext.setClickable(false);
                } else {
                    tvNext.setClickable(true);
                    tvPrev.setClickable(true);
                }
                InitData(position + 1, length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        tvPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
            }
        });

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        });

        tvShowAllQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        tvHideQuestionBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        RenderQuestionBottomSheet(topic);
    }

    private void InitUI() {
        imgQuestionTopic = findViewById(R.id.imgQuestionTopic);

        tvNumber = findViewById(R.id.txtNumber);
        tvPrev = findViewById(R.id.txtPrev);
        tvNext = findViewById(R.id.txtNext);
        tvShowAllQuestion = findViewById(R.id.txtShowAllQuestion);

        tvHideQuestionBottomSheet = findViewById(R.id.txtHideQuestionBottomSheet);

        viewPager2 = findViewById(R.id.vpgQuestion);
        lnBottomSheet = findViewById(R.id.bottom_sheet_layout);
        questionBottomSheetRecyclerView = findViewById(R.id.rcvQuestionBottomSheet);

        bottomSheetBehavior = BottomSheetBehavior.from(lnBottomSheet);
    }

    private void InitData(int currentQuestion, int length) {
        tvNumber.setText("Câu " + currentQuestion + "/" + length);
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
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, questionList);
        viewPager2.setAdapter(viewPagerAdapter);
    }

    private void RenderQuestionBottomSheet(Topic topic) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        questionBottomSheetRecyclerView.setLayoutManager(linearLayoutManager);

        List<Question> questionList = GetListQuestion();
        questionBottomSheetAdapter = new QuestionBottomSheetAdapter(context, questionList, topic);
        questionBottomSheetRecyclerView.setAdapter(questionBottomSheetAdapter);
    }

    public void onClickBackToMain(View view) {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}