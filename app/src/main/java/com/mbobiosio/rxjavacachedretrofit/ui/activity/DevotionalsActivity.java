package com.mbobiosio.rxjavacachedretrofit.ui.activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.mbobiosio.rxjavacachedretrofit.R;
import com.mbobiosio.rxjavacachedretrofit.data.api.RetrofitManager;
import com.mbobiosio.rxjavacachedretrofit.model.DailyModel;
import com.mbobiosio.rxjavacachedretrofit.model.Item;
import com.mbobiosio.rxjavacachedretrofit.ui.adapter.DevotionalsAdapter;
import com.mbobiosio.rxjavacachedretrofit.utils.DevUtil;
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
public class DevotionalsActivity extends AppCompatActivity {

    public final static String TAG = "Devotionals";

    @BindView(R.id.devotionals)
    RecyclerView mDailyList;

    private LinearLayoutManager mLayout;
    private ArrayList<Item> mDevotionals = new ArrayList<>();
    private DevotionalsAdapter mDevotionalAdapter;
    DevUtil mCommonUtils;
    RetrofitManager mRetrofitManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_devotionals);
        ButterKnife.bind(this);

        mRetrofitManager = new RetrofitManager(this);
        mCommonUtils = new DevUtil(mRetrofitManager);

        initDevotionals();
        fetchDevotional();
        Log.d("Devotionals", "Oncreate");

    }

    private void initDevotionals() {
        mDevotionalAdapter = new DevotionalsAdapter(this);
        mLayout = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mDailyList.setLayoutManager(mLayout);
        mDailyList.setAdapter(mDevotionalAdapter);
    }

    public void fetchDevotional() {
        /*Call<DailyModel> call = mService.getDevotional();
        call.enqueue(new Callback<DailyModel>() {
            @Override
            public void onResponse(@NonNull Call<DailyModel> call, @NonNull Response<DailyModel> response) {
                DailyModel model = response.body();
                assert model != null;
                mDevotionals = model.getChannel().getItems();

                try {
                    DevotionalRSS adapter = new DevotionalRSS(mDevotionals, R.layout.devotionals_item, getBaseActivity());
                    mDailyList.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DailyModel> call, @NonNull Throwable t) {
                Timber.d(t);
            }
        });*/


/*


        mDisposable.add(mService.getDevotional()
                .subscribeOn(Schedulers.io()) // "work" on io thread
                .observeOn(AndroidSchedulers.mainThread()) // "listen" on UIThread
                .map(new Function<DailyModel, List<Item>>() {
                    @Override
                    public List<Item> apply(
                            @io.reactivex.annotations.NonNull final DailyModel response)
                            throws Exception {
                        return response.getChannel().getItems();
                    }
                })
                .subscribe(new Consumer<List<Item>>() {
                    @Override
                    public void accept(
                            @io.reactivex.annotations.NonNull final List<Item> devotionalsList)
                            throws Exception {
                        if (devotionalsList.isEmpty() && !isNetworkConnected()){
                            mInternetNotice.setVisibility(View.VISIBLE);
                        }
                        else {
                            mDevotionals.addAll(devotionalsList);
                            mWeekAdapter.set(mLessons);
                        }
                    }
                })

        );
*/

        mCommonUtils.getDevsCache() //From cache
                .doFinally(() -> {
                    mCommonUtils.getDevs() // From Network

                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<DailyModel, List<Item>>() {
                                @Override
                                public List<Item> apply(
                                        @io.reactivex.annotations.NonNull final DailyModel response)
                                        throws Exception {
                                    Log.d("Devotionals", ""+response.getChannel().getItems());
                                    return response.getChannel().getItems();
                                }

                            })
                            .subscribe(new Consumer<List<Item>>() {
                                @Override
                                public void accept(
                                        @io.reactivex.annotations.NonNull final List<Item> devotionals)
                                        throws Exception {
                                    mDevotionals.addAll(devotionals);
                                    mDevotionalAdapter.setData(mDevotionals);
                                }

                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    throwable.printStackTrace();
                                }
                            });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<DailyModel, List<Item>>() {
                    @Override
                    public List<Item> apply(
                            @io.reactivex.annotations.NonNull final DailyModel response)
                            throws Exception {
                        return response.getChannel().getItems();
                    }

                })
                .subscribe(new Consumer<List<Item>>() {
                    @Override
                    public void accept(
                            @io.reactivex.annotations.NonNull final List<Item> dataList)
                            throws Exception {

                        mDevotionals.addAll(dataList);
                        mDevotionalAdapter.setData(mDevotionals);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

    }
}
