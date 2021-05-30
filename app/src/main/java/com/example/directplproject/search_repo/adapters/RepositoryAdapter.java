package com.example.directplproject.search_repo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.directplproject.R;
import com.example.directplproject.search_repo.models.Item;
import com.example.directplproject.search_repo.models.RepositoryModel;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.GithubViewHolder> {
    List<Item> repos;

    public RepositoryAdapter(List<Item> repos) {
        this.repos = repos;
    }

    @NonNull
    @Override
    public RepositoryAdapter.GithubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GithubViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryAdapter.GithubViewHolder holder, int position) {
        holder.bindRepos(repos.get(position));
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }


    public class GithubViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_name)
        TextView name;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_description)
        TextView description;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_language)
        TextView language;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_url)
        TextView url;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_starcount)
        TextView starCount;
        @SuppressLint("NonConstantResourceId")


        Context context;

        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            context = itemView.getContext();
        }

        @SuppressLint("SetTextI18n")
        public void bindRepos(Item item) {
            name.setText(item.getName());
            description.setText(item.getDescription());
            language.setText(item.getLanguage());
            url.setText(item.getHtmlUrl());
            starCount.setText(item.getStargazersCount().toString());
        }
    }
}
