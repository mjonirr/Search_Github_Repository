package com.example.directplproject.search_repo.views;

import com.example.directplproject.search_repo.models.Item;
import com.example.directplproject.search_repo.models.RepositoryModel;

import java.util.ArrayList;

public interface ISearchRepoView {
    void getRepo(ArrayList<Item> repositoryList);
    void showProgress();
    void hideProgress();
}
