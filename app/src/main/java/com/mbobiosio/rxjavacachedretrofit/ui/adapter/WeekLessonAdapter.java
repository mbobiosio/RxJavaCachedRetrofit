package com.mbobiosio.rxjavacachedretrofit.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        if (position == 0) {
            holder.mCategory.setText(mContext.getString(R.string.title_junior));
        } else if (position == 1) {
            holder.mCategory.setText(mContext.getString(R.string.title_senior));
        }
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