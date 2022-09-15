package com.example.homebudget.View.FragmentView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homebudget.R;
import com.example.homebudget.databinding.FragmentPlansBinding;

public class PlansFragment extends Fragment {

    FragmentPlansBinding fragmentPlansBinding;

    public PlansFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPlansBinding = FragmentPlansBinding.inflate(inflater, container, false);
        View fragmentView = fragmentPlansBinding.getRoot();


        return fragmentView;
    }
}