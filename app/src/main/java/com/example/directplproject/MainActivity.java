package com.example.directplproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.directplproject.base.ApiClient;
import com.example.directplproject.base.ApiService;
import com.example.directplproject.base.BaseActivity;
import com.example.directplproject.base.Utils;
import com.example.directplproject.search_repo.adapters.RepositoryAdapter;
import com.example.directplproject.search_repo.listener.RecyclerTouchListener;
import com.example.directplproject.search_repo.models.Item;
import com.example.directplproject.search_repo.models.RepositoryModel;
import com.example.directplproject.search_repo.presenters.SearchPresenter;
import com.example.directplproject.search_repo.views.ISearchRepoView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends BaseActivity implements ISearchRepoView {


    @BindView(R.id.rv_repos)
    RecyclerView recyclerView;
    @BindView(R.id.et_searchrepo)
    EditText etSearchRepo;
    @BindView(R.id.btn_search)
    ImageButton btnSearch;
    @BindView(R.id.img_clear_search)
    ImageButton clearSearch;

    private final CompositeDisposable disposable = new CompositeDisposable();
    private SearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ApiService apiService = ApiClient.getClient(Utils.setBaseUrl()).create(ApiService.class);
        mPresenter = new SearchPresenter(this, this, disposable, apiService);
    }

    @Override
    protected void onStart() {
        super.onStart();

        etSearchRepo.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSearchRepo.length() != 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                } else {
                    clearSearch.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.btn_search, R.id.img_clear_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                String search = etSearchRepo.getText().toString();
                mPresenter.getRepository(search);
                break;
            case R.id.img_clear_search:
                etSearchRepo.setText("");
                break;
        }
    }

    @Override
    public void getRepo(ArrayList<Item> repositoryList) {
        RepositoryAdapter rvAdapter = new RepositoryAdapter(repositoryList);
        Utils.createRV(recyclerView, this, rvAdapter);
        mPresenter.setTouchListener(recyclerView, repositoryList);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }
}