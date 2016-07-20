package com.romanus.cardiotracker.ui.workout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romanus.cardiotracker.R;

/**
 * Created by rursu on 20.07.16.
 */
public class WorkoutFragment extends Fragment {

    public static WorkoutFragment newInstance() {

        Bundle args = new Bundle();
        
        WorkoutFragment fragment = new WorkoutFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        return view;
    }
}
