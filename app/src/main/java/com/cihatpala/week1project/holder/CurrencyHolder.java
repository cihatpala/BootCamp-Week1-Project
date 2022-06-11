package com.cihatpala.week1project.holder;

import static com.cihatpala.week1project.helper.CoinHelper.coinPriceDoubleFormat;

import androidx.recyclerview.widget.RecyclerView;

import com.cihatpala.week1project.databinding.CurrencyItemLayoutBinding;
import com.cihatpala.week1project.models.CurrencyModel;

public class CurrencyHolder extends RecyclerView.ViewHolder {

    CurrencyItemLayoutBinding binding;

    public CurrencyHolder(CurrencyItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(CurrencyModel currency) {
        binding.currencyBaseAmount.setText("$" + coinPriceDoubleFormat(currency.currencyPrice));
        binding.currencyLongText.setText(currency.currencyName);
        binding.currencyShortAmount.setText("$" + coinPriceDoubleFormat(currency.currencyPrice));
        binding.currencyShortText.setText(currency.currency + " — ");

    }
}
