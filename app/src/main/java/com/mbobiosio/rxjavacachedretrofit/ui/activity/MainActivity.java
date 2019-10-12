package com.mbobiosio.rxjavacachedretrofit.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";

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


        initLessons();

        Log.d(TAG, ""+mDataList.size());

        if (!isConnected() && mUtils.getCachedLessons() == null) {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (isConnected() && mUtils.getCachedLessons() != null){
            runCache();
        } else {
            runInternet();
        }
    }

    private void runCache() {
        mUtils.getCachedLessons() //From cache
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<WeekResponse, List<WeekModel>>() {
                    @Override
                    public List<WeekModel> apply(
                            @NonNull final WeekResponse response)
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

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    private void runInternet() {
        mUtils.getLessons() // From Network
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<WeekResponse, List<WeekModel>>() {
                    @Override
                    public List<WeekModel> apply(
                            @NonNull final WeekResponse response)
                            throws Exception {

                        return response.getResult();

                    }

                })
                .subscribe(new Consumer<List<WeekModel>>() {
                    @Override
                    public void accept(
                            @NonNull final List<WeekModel> lessonList)
                            throws Exception {
                        Log.d(TAG, "List " + lessonList);

                        mDataList.addAll(lessonList);
                        mWeekAdapter.set(mDataList);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                Intent intent = new Intent(this, DevotionalsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
