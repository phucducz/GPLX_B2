package com.example.gplx_b2.Controller;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gplx_b2.Modal.Topic;
import com.example.gplx_b2.R;
import com.example.gplx_b2.myInterface.IClickTopicItemListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
    private List<Topic> topicList;
    private IClickTopicItemListener iClickTopicItemListener;
    private Context context;

    public TopicAdapter(Context context, List<Topic> topicList, IClickTopicItemListener listener) {
        this.context = context;
        this.topicList = topicList;
        this.iClickTopicItemListener = listener;
    }

//    public void setData(List<Topic> topicList) {
//        this.topicList = topicList;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.topic_item, parent, false);

        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        if (topic == null) {
            return;
        }

        String name = topic.getImage();
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());

        holder.resourceId.setImageResource(imageResource);
        holder.title.setText(topic.getTitle());
        holder.bar.setProgress(topic.getQuantity());
        holder.numberQuestion.setText(topic.getSubTitle() + " câu hỏi");

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickTopicItemListener.onClickItemTopic(topic);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (topicList != null) {
            return topicList.size();
        }
        return 0;
    }

    public void release() {
        context = null;
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {
        private ImageView resourceId;
        private TextView title;
        private ProgressBar bar;
        private TextView numberQuestion;
        private RelativeLayout relativeLayout;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);

            resourceId = itemView.findViewById(R.id.imgTopic);
            title = itemView.findViewById(R.id.txtTitle);
            bar = itemView.findViewById(R.id.barProgress);
            numberQuestion = itemView.findViewById(R.id.txtQuestionNumber);
            relativeLayout = itemView.findViewById(R.id.layout_item);
        }
    }
}