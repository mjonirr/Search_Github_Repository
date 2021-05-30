package com.example.directplproject.search_repo.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.directplproject.base.ApiService;
import com.example.directplproject.search_repo.listener.RecyclerTouchListener;
import com.example.directplproject.search_repo.models.Item;
import com.example.directplproject.search_repo.models.RepositoryModel;
import com.example.directplproject.search_repo.views.ISearchRepoView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter {

    private Context context;
    private final ISearchRepoView mView;
    private final CompositeDisposable disposable;
    private final ApiService apiService;
    ArrayList<Item> repos = new ArrayList<>();

    public SearchPresenter(Context context, ISearchRepoView mView, CompositeDisposable disposable, ApiService apiService) {
        this.context = context;
        this.mView = mView;
        this.apiService = apiService;
        this.disposable = disposable;
    }

    public void getRepository(String repository) {
        mView.showProgress();
        disposable.add(apiService.getRepository(
                repository,
                100
        )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::getRepositoryArray, this::handleError));
    }

    public void getRepositoryArray(RepositoryModel response) {
        mView.hideProgress();

        for (Item q : response.getItems()) {
            String name = q.getName();
            String description = q.getDescription();
            String language = q.getLanguage();
            String url = q.getHtmlUrl();
            Integer stars = q.getStargazersCount();

            Item item = new Item(name, description, language, url, stars);

            repos.add(item);
        }

        mView.getRepo(repos);
    }

    private void handleError(Throwable error) {
        Toast.makeText(context, "Something happened. Please try again", Toast.LENGTH_SHORT).show();
        mView.hideProgress();
    }

    public void setTouchListener(RecyclerView recyclerView, ArrayList<Item> repositoryList) {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Item item = repositoryList.get(position);
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.getHtmlUrl())));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
