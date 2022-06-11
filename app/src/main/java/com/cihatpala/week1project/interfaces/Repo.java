package com.cihatpala.week1project.interfaces;

import com.cihatpala.week1project.models.CurrencyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Repo {
    @GET("v2/coins")
    Call<List<CurrencyModel>> getCoin();
}
