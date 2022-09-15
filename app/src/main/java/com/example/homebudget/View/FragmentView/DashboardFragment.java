package com.example.homebudget.View.FragmentView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homebudget.R;
import com.example.homebudget.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    FragmentDashboardBinding fragmentDashboardBinding;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false);
        View fragmentView = fragmentDashboardBinding.getRoot();


        return fragmentView;
    }
}