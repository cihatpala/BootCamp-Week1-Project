package com.cihatpala.week1project.adapter;

import static com.cihatpala.week1project.helper.CoinHelper.changeDrawableColor;
import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cihatpala.week1project.R;
import com.cihatpala.week1project.databinding.CurrencyItemLayoutBinding;
import com.cihatpala.week1project.holder.CurrencyHolder;
import com.cihatpala.week1project.models.CurrencyModel;

import java.util.ArrayList;
import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyHolder> {

    ArrayList<CurrencyModel> coinsList;
    CurrencyItemLayoutBinding binding;
    Context context;

    public CurrencyAdapter(List<CurrencyModel> coinsList, Context context) {
        this.coinsList = (ArrayList<CurrencyModel>) coinsList;
        this.context = context;
    }

    @NonNull
    @Override
    public CurrencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CurrencyItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CurrencyHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CurrencyHolder holder, int position) {
        CurrencyModel currencyItem = coinsList.get(position);
        holder.bind(currencyItem);
    }

    @Override
    public int getItemCount() {
        return coinsList.size();
    }
}
