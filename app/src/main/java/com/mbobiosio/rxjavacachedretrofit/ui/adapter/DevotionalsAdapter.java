package com.mbobiosio.rxjavacachedretrofit.ui.adapter;

import android.app.Activity;
import android.text.Html;
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
import com.mbobiosio.rxjavacachedretrofit.model.Item;
import com.mbobiosio.rxjavacachedretrofit.utils.Constants;
import com.mbobiosio.rxjavacachedretrofit.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mbuodile Obiosio on Aug 14,2019
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public class DevotionalsAdapter extends RecyclerView.Adapter<DevotionalsAdapter.DevotionalViewHolder> {
    private final Activity mContext;
    private List<Item> mDataList;

    public DevotionalsAdapter(Activity context) {
        this.mDataList = new ArrayList<>();
        this.mContext = context;
    }

    @NonNull
    @Override
    public DevotionalViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.devotionals_item, parent, false);

        return new DevotionalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DevotionalViewHolder holder, int position) {
        Item item = mDataList.get(position);
        holder.set(item);
        holder.mDailyItem.setOnClickListener(v -> {
            Toast.makeText(mContext, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
        });

    }

    public void setData(List<Item> itemList) {
        this.mDataList = itemList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        /*if (mContext.getIntent().hasExtra("all")) {
            return mDataList.size();
        } else {
            //return items.size();
            return (mDataList != null && mDataList.size() >= 2) ? 1 : mDataList != null ? mDataList.size() : 0;
        }*/
        return mDataList.size();
    }

    public class DevotionalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_title)
        TextView mTitle;
        @BindView(R.id.date)
        TextView mDate;
        @BindView(R.id.item_notes)
        TextView mDescription;
        @BindView(R.id.devotional_img)
        ImageView mImageView;
        @BindView(R.id.all_devotionals)
        ImageView mAllItems;
        @BindView(R.id.daily_item)
        CardView mDailyItem;


        public DevotionalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (mContext.getIntent().hasExtra("all")) {
                mAllItems.setVisibility(View.GONE);
                mImageView.setVisibility(View.GONE);
            }
        }

        public void set(Item item) {
            //UI setting code
            mTitle.setText(item.getTitle());
            mDate.setText(item.getPubDate());
            mDescription.setText(Html.fromHtml(item.getDescriptionPlaintext()));
            Utils.loadGlideImage(mContext, mImageView, Constants.DEVOTIONAL_IMAGE);
        }
    }
}
