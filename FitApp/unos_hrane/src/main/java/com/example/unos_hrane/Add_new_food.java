package com.example.unos_hrane;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;



public class Add_new_food extends Fragment {

    private Add_new_food_ViewModel add_new_food_viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //add_new_food_viewModel = new ViewModelProvider(this).get(Add_new_food_ViewModel.class);
    }


}
