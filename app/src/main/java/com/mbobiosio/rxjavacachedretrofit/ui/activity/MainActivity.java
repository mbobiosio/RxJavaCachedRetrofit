package com.mbobiosio.rxjavacachedretrofit.ui.activity;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mbobiosio.rxjavacachedretrofit.R;
import com.mbobiosio.rxjavacachedretrofit.data.api.RetrofitManager;
import com.mbobiosio.rxjavacachedretrofit.model.WeekModel;
import com.mbobiosio.rxjavacachedretrofit.model.WeekResponse;
import com.mbobiosio.rxjavacachedretrofit.ui.adapter.WeekLessonAdapter;
import com.mbobiosio.rxjavacachedretrofit.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.week_lesson_list)
    RecyclerView mWeekList;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<WeekModel> mDataList = new ArrayList<>();
    private WeekLessonAdapter mWeekAdapter;
    RetrofitManager mRetrofitManager;
    Utils mUtils;
    Utils mUtilsCached;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRetrofitManager = new RetrofitManager(this);
        mUtils = new Utils(mRetrofitManager);
        mUtilsCached = new Utils(mRetrofitManager);

        mUtilsCached.getCachedLessons();

        initLessons();

        fetchData();

    }

    private void fetchData() {

        mUtils.getCachedLessons()
                .doFinally(() -> {
                    mUtils.getLessons() // From Network
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<WeekResponse, List<WeekModel>>() {
                                @Override
                                public List<WeekModel> apply(
                                        @io.reactivex.annotations.NonNull final WeekResponse response)
                                        throws Exception {
                                    return response.getResult();
                                }

                            })
                            .subscribe(new Consumer<List<WeekModel>>() {
                                @Override
                                public void accept(
                                        @io.reactivex.annotations.NonNull final List<WeekModel> lessonList)
                                        throws Exception {

                                    mDataList.addAll(lessonList);
                                    mWeekAdapter.set(mDataList);
                                }

                            });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<WeekResponse, List<WeekModel>>() {
                    @Override
                    public List<WeekModel> apply(
                            @io.reactivex.annotations.NonNull final WeekResponse response)
                            throws Exception {
                        return response.getResult();
                    }

                })
                .subscribe(new Consumer<List<WeekModel>>() {
                    @Override
                    public void accept(
                            @io.reactivex.annotations.NonNull final List<WeekModel> lessonList)
                            throws Exception {

                        mDataList.addAll(lessonList);
                        mWeekAdapter.set(mDataList);
                    }

                });


    }

    private boolean isConnected() {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager) getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.w("TAG", e.toString());
        }

        return false;
    }

    private void initLessons() {
        mWeekAdapter = new WeekLessonAdapter(this);
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mWeekList.setLayoutManager(mLayoutManager);
        mWeekList.setAdapter(mWeekAdapter);
    }
}
