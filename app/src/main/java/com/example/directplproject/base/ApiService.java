package com.example.directplproject.base;

import com.example.directplproject.search_repo.models.RepositoryModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    @GET("search/repositories")
    Single<RepositoryModel> getRepository(
            @Query("q") String search_query,
            @Query("per_page") Integer per_page
    );
}
