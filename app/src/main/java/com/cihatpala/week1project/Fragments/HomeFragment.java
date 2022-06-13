package com.cihatpala.week1project.Fragments;

import static com.cihatpala.week1project.helper.CoinHelper.colors;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cihatpala.week1project.R;
import com.cihatpala.week1project.adapter.CurrencyAdapter;
import com.cihatpala.week1project.databinding.FragmentHomeBinding;
import com.cihatpala.week1project.interfaces.Repo;
import com.cihatpala.week1project.models.CurrencyModel;
import com.cihatpala.week1project.services.ApiClient;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    FirebaseAuth mAuth;
    Repo repo;
    CurrencyAdapter adapter;
    List<CurrencyModel> coinsList;
    List<CurrencyModel> coinsListBad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colors = new HashMap<>();
        colors.put(1, R.drawable.ic_rectangle1);
        colors.put(2, R.drawable.ic_rectangle2);
        colors.put(3, R.drawable.ic_rectangle3);
        colors.put(4, R.drawable.ic_rectangle4);
        mAuth = FirebaseAuth.getInstance();
        repo = ApiClient.getClient().create(Repo.class);
    }

    private void getAllCurrency() {
        Call<List<CurrencyModel>> call = repo.getCoin();
        call.enqueue(new Callback<List<CurrencyModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<CurrencyModel>> call, Response<List<CurrencyModel>> response) {
                coinsListBad = response.body();
                assert coinsListBad != null;
                for (int i = 0; i < coinsListBad.size(); i++) {
                    if (coinsListBad.get(i).currencyPrice > 0.0) { //
                        coinsList.add(coinsListBad.get(i));
                    }
                }
                System.out.println("onResponse -> " + response + "\ncoinList size = " + coinsList.size() + "\ncoinListBad size = " + coinsListBad.size());

                adapter = new CurrencyAdapter(coinsList, getActivity().getApplicationContext());
                binding.rvCurrency.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CurrencyModel>> call, Throwable t) {
                System.out.println("onFailure -> " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, null, false);
        View view = binding.getRoot();
        binding.rvCurrency.setLayoutManager(new GridLayoutManager(getContext(), 2));
        coinsList = new ArrayList<>();

        getAllCurrency();
        binding.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment());
            }
        });

        return view;
    }
}