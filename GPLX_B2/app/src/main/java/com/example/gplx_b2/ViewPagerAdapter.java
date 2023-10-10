package com.example.gplx_b2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.gplx_b2.Modal.Question;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private List<Question> questionList;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Question> list) {
        super(fragmentActivity);
        this.questionList = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (questionList == null || questionList.isEmpty())
            return null;

        Question question = questionList.get(position);
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("question_object", question);
        questionFragment.setArguments(bundle);

        return questionFragment;
    }

    @Override
    public int getItemCount() {
        if (questionList != null)
            return questionList.size();
        return 0;
    }
}
