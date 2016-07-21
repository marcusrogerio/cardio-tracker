package com.romanus.cardiotracker.ui.workout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.romanus.cardiotracker.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rursu on 20.07.16.
 */
public class WorkoutFragment extends Fragment {

    @BindView(R.id.et_value_min_heart_value)
    EditText minHeartEditText;

    @BindView(R.id.et_value_max_heart_value)
    EditText maxHeartEditText;

    @BindView(R.id.et_value_duration)
    EditText durationEditText;

    @BindView(R.id.et_value_distance)
    EditText distanceEditText;

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
        ButterKnife.bind(this, view);

        return view;
    }


}
