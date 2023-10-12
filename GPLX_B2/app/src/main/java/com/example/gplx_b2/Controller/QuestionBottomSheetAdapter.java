package com.example.gplx_b2.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gplx_b2.Modal.Question;
import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionBottomSheetAdapter extends RecyclerView.Adapter<QuestionBottomSheetAdapter.ViewHolder> {
    Context context;
    List<Question> questionList;
    Topic topic;

    public QuestionBottomSheetAdapter(Context context, List<Question> list, Topic topic) {
        this.context = context;
        this.questionList = list;
        this.topic = topic;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.question_bottom_sheet_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questionList.get(position);
        if (question == null) {
            return;
        }

        String name = topic.getImage();
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        holder.imgTopicBottomSheet.setImageResource(imageResource);

        holder.tvQuestionNumber.setText("Câu " + (position + 1) + "/" + questionList.size());
        holder.tvQuestionBottomSheet.setText(question.getQuestion());
    }

    @Override
    public int getItemCount() {
        if (questionList != null)
            return questionList.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lnQuestionItem;
        private ImageView imgTopicBottomSheet;
        private TextView tvQuestionNumber;
        private TextView tvQuestionBottomSheet;
        private TextView tvLearned;
        private ImageView imgLearned;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lnQuestionItem = itemView.findViewById(R.id.lnQuestionItem);
            imgTopicBottomSheet = itemView.findViewById(R.id.imgTopicBottomSheet);
            tvQuestionNumber = itemView.findViewById(R.id.txtQuestionNumberBottomSheet);
            tvQuestionBottomSheet = itemView.findViewById(R.id.txtQuestionBottomSheet);
            tvLearned = itemView.findViewById(R.id.txtLearned);
            imgLearned = itemView.findViewById(R.id.imgLearned);
        }
    }
}
