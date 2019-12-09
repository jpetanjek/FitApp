package com.example.unos_hrane;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.core.entities.Namirnica;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Add_new_food extends Fragment {
    private Callback<Integer> callback;
    private Add_new_food_ViewModel add_new_food_viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        add_new_food_viewModel = ViewModelProviders.of(this).get(Add_new_food_ViewModel.class);
        final LifecycleOwner owner = this;
        add_new_food_viewModel = new Add_new_food_ViewModel(getActivity().getApplication(), new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                add_new_food_viewModel.namirnicaLiveData.observe(owner, new Observer<Namirnica>() {
                    @Override
                    public void onChanged(Namirnica namirnica) {
                        //update ui
                        Toast.makeText(getContext(),"onResponse i On Changed",Toast.LENGTH_LONG).show();
                        System.out.println("OnChanged");
                    }
                });
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

    }


}
