package com.android.superplayer.service.presenter.impl;

import android.content.Context;

import com.android.superplayer.config.LogUtil;
import com.android.superplayer.service.entity.BannerListBean;
import com.android.superplayer.service.manager.DataManager;
import com.android.superplayer.service.presenter.BasePresenter;
import com.android.superplayer.service.presenter.IBannerListPresenter;
import com.android.superplayer.service.view.impl.IBannerListView;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * anther: created by zuochunsheng on 2018/9/3 09 : 54
 * descript :
 * mCompositeSubscription 方式 要 onCreate 和 onDestroy
 * BaseService.getBaseService() 方式不需要
 */
public class BannerListPresenterImpl extends BasePresenter implements IBannerListPresenter {


    private IBannerListView activity;

    public BannerListPresenterImpl(IBannerListView activity) {
        this.activity = activity;
        super.onCreate();
    }

    @Override
    public void getBannerList(Context context, HashMap<String, String> requestBody) {



        super.mCompositeSubscription.add(DataManager.getInstance(context)
                .getBannerList(requestBody)
                .subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BannerListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("网络请求失败：" + e.getMessage());
                    }

                    @Override
                    public void onNext(BannerListBean responseBody) {
                        //网络请求成功 有返回 不管对错
                        LogUtil.e("next bannerList mCompositeSubscription");

                        try {
                            //String s = responseBody.string();//不能用 toString() ;

                            LogUtil.e(responseBody);// 值为null 的不会打印出来
                            activity.bannerListInfo(responseBody);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );



//        BaseService.getBaseService()
//                .getBannerList(requestBody)
//                .subscribeOn(Schedulers.newThread()) //子线程访问网络
//
//                .observeOn(AndroidSchedulers.mainThread()) //回调到主线程
//                .subscribe(new Subscriber<BannerListBean>() {
//                    @Override
//                    public void onCompleted() {
//                        //LogUtil.e("成功");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.e("网络请求失败：" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(BannerListBean responseBody) {
//                        //网络请求成功 有返回 不管对错
//                        LogUtil.e("next bannerList");
//
//                        try {
//                            //String s = responseBody.string();//不能用 toString() ;
//
//                            LogUtil.e(responseBody);// 值为null 的不会打印出来
//                            activity.bannerListInfo(responseBody);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                });
//

    }
}
