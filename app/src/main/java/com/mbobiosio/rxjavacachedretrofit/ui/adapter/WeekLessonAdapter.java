package com.mbobiosio.rxjavacachedretrofit.ui.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mbobiosio.rxjavacachedretrofit.R;
import com.mbobiosio.rxjavacachedretrofit.model.WeekModel;
import com.mbobiosio.rxjavacachedretrofit.utils.Constants;
import com.mbobiosio.rxjavacachedretrofit.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mbuodile Obiosio on Aug 11,2019
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public class WeekLessonAdapter extends RecyclerView.Adapter<WeekLessonAdapter.WeekLessonHolder> {
    private Context mContext;
    private List<WeekModel> mList;
    private long DURATION = 100;
    private boolean attach = true;

    public WeekLessonAdapter(Context context) {
        this.mList = new ArrayList<>();
        this.mContext = context;
    }

    @NonNull
    @Override
    public WeekLessonHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                               int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.week_lesson_item, parent, false);
        return new WeekLessonHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekLessonHolder holder, int position) {
        WeekModel item = mList.get(position);
        holder.set(item);
        holder.mWeekItem.setOnClickListener(v -> {
            if (position == 0) {
                Toast.makeText(mContext, mContext.getString(R.string.title_junior).concat(" ")+item.getTopic(), Toast.LENGTH_SHORT).show();
            } else if (position == 1) {
                Toast.makeText(mContext, mContext.getString(R.string.title_senior).concat(" ")+item.getTopic(), Toast.LENGTH_SHORT).show();
            }
        });

        if (position == 0) {
            holder.mCategory.setText(mContext.getString(R.string.title_junior));
        } else if (position == 1) {
            holder.mCategory.setText(mContext.getString(R.string.title_senior));
        }

        animateList(holder.itemView, position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    private void animateList(View itemView, int i) {
        if(!attach){
            i = -1;
        }
        boolean not_first_item = i == -1;
        i = i + 1;
        itemView.setTranslationX(-400f);
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", -400f, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1.f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animatorTranslateY.setStartDelay(not_first_item ? DURATION : (i * DURATION));
        animatorTranslateY.setDuration((not_first_item ? 2 : 1) * DURATION);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();
    }

    public void update(ArrayList<WeekModel> dataList) {
        this.mList = dataList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        //return mList.size();
        return (mList != null && mList.size() >= 2) ? 2 : mList != null ? mList.size() : 0;
    }

    public void set(List<WeekModel> items) {
        //UI setting code
        //items.clear();
        //items.addAll(mList);
        this.mList = items;

        this.notifyDataSetChanged();
    }


    public class WeekLessonHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lesson_count)
        TextView mCount;
        @BindView(R.id.lesson_title)
        TextView mTopic;
        @BindView(R.id.lesson_verse)
        TextView mVerse;
        @BindView(R.id.lesson_category)
        TextView mCategory;
        @BindView(R.id.week_lesson_item)
        CardView mWeekItem;
        @BindView(R.id.week_lesson_img)
        ImageView mImage;
        @BindView(R.id.share)
        ImageView mShare;

        private WeekLessonHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void set(WeekModel item) {
            //UI setting code
            mCount.setText(item.getCount());
            mTopic.setText(item.getTopic());
            mVerse.setText(item.getVerse());

            Utils.loadGlideImage(mContext, mImage, Constants.LESSON_IMAGE);
        }
    }
}