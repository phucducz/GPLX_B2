package com.example.gplx_b2.Controller;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.gplx_b2.R;
import com.example.gplx_b2.myInterface.IClickAnswerItemListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    Context context;
    final private List<String> answerList;
    final private IClickAnswerItemListener iClickAnswerItemListener;
    final private ArrayList<Integer> selectCheck = new ArrayList<>();
    private boolean isCorrect = false;
    private ArrayList<String> answerChooseList = new ArrayList<>();

    public AnswerAdapter(Context context, List<String> answerList, IClickAnswerItemListener listener) {
        this.context = context;
        this.answerList = answerList;
        this.iClickAnswerItemListener = listener;

        for (int i = 0; i < answerList.size(); i++) {
            selectCheck.add(0);
            answerChooseList.add(null);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.answer_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String answer = answerList.get(position);
        if (answer == null || answer.isEmpty())
            return;

        //is checked - Single selection
        if (selectCheck.get(position) == 1)
            holder.radioButton.setChecked(true);
            //unchecked
        else
            holder.radioButton.setChecked(false);
        holder.tvAnswerNumber.setText((position + 1) + " - ");
        holder.tvAnswer.setText(answer + ".");

//        if (answerChooseList.get(position) != null) {
//            enableDisableView(holder.lnLinearGroup, false);
//            Log.d("Check", "in");
//        }

        //events
        int adapterPosition = holder.getBindingAdapterPosition();
        holder.lnAnswerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickAnswerItemListener.onClickCheckBox(selectCheck, adapterPosition);
                answerChooseList.set(adapterPosition, answer);
                notifyDataSetChanged();
            }
        });
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCorrect = iClickAnswerItemListener.onCheckedChange(answer, isChecked, adapterPosition);
                if (isCorrect)
                    holder.lnAnswerItem.setBackgroundColor(Color.parseColor("#1dd1a1"));
                else
                    holder.lnAnswerItem.setBackgroundColor(Color.parseColor("#e74c3c"));

                holder.tvAnswer.setTextColor(Color.parseColor("#f5f6fa"));
                holder.tvAnswerNumber.setTextColor(Color.parseColor("#f5f6fa"));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (answerList != null)
            return answerList.size();
        return 0;
    }

    public void release() {
        context = null;
    }

    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final private RadioButton radioButton;
        final private TextView tvAnswerNumber;
        final private TextView tvAnswer;
        final private LinearLayout lnAnswerItem, lnLinearGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.cbAnswer);
            tvAnswerNumber = itemView.findViewById(R.id.txtAnswerNumber);
            tvAnswer = itemView.findViewById(R.id.txtAnswer);
            lnAnswerItem = itemView.findViewById(R.id.lnAnswerItem);
            lnLinearGroup = itemView.findViewById(R.id.lnRecyclerGroup);
        }
    }
}
