package com.example.gplx_b2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gplx_b2.Controller.AnswerAdapter;
import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.myInterface.IClickAnswerItemListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class QuestionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public QuestionFragment() {
        // Required empty public constructor
    }

    private Context context;
    private Question question;
    private View layout;
    private AnswerAdapter answerAdapter;
    private RecyclerView answerRecyclerView;
    private TextView tvQuestion;

    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_question, container, false);
        InitUI(layout);

        question = GetDataQuestion();
        if (question != null) {
            tvQuestion.setText(question.getQuestion());

            List<String> answerList = question.getAnswerList();
            RenderListAnswer(answerList);
        }

        return layout;
    }

    private void InitUI(View layout) {
        answerRecyclerView = layout.findViewById(R.id.rcvAnswer);
        tvQuestion = layout.findViewById(R.id.txtQuestion);
    }

    private Question GetDataQuestion() {
        Bundle bundle = getArguments();
        if (bundle != null)
            question = (Question) bundle.get("question_object");

        return question;
    }

    private void RenderListAnswer(List<String> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        answerRecyclerView.setLayoutManager(linearLayoutManager);

        answerAdapter = new AnswerAdapter(context, StandardListAnswer(list), new IClickAnswerItemListener() {
            @Override
            public void onClickCheckBox(ArrayList<Integer> selectCheck, int adapterPosition) {
                for (int i = 0; i < selectCheck.size(); i++) {
                    if (i == adapterPosition) {
                        selectCheck.set(i, 1);
                    } else {
                        selectCheck.set(i, 0);
                    }
                }
            }

            @Override
            public boolean onCheckedChange(String answer, boolean isChecked, int adapterPosition) {
                boolean isCorrect = false;
                if (isChecked) {
                    Question question = GetDataQuestion();
                    String answerCorrect = question.getAnswerCorrect();

                    if (answer.equals(answerCorrect))
                        isCorrect = true;
                }

                return isCorrect;
            }
        });
        answerRecyclerView.setAdapter(answerAdapter);
    }

    private List<String> StandardListAnswer(List<String> list) {
        List<String> newAnswerList = new ArrayList<>();

        for (String answer : list) {
            if (answer != null)
                newAnswerList.add(answer);
        }

        //set height for recycler view without RecyclerView parent container height as wrap_content
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) answerRecyclerView.getLayoutParams();
        layoutParams.height = newAnswerList.size() * 200;
        answerRecyclerView.setLayoutParams(layoutParams);

        return newAnswerList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (answerAdapter != null) {
            answerAdapter.release();
        }
    }
}