package com.romanus.cardiotracker.ui.workout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.romanus.cardiotracker.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rursu on 20.07.16.
 */
public class WorkoutFragment extends Fragment {

    @BindView(R.id.lv_parameters_list)
    ListView paramenetrsListView;

    private Map<WorkoutParameter, String> values = new HashMap<>();

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

        initList();
        return view;
    }

    private void initList() {
        ParametersAdapter parametersAdapter = new ParametersAdapter();
        paramenetrsListView.setAdapter(parametersAdapter);
    }

    class ParametersAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return WorkoutParameter.values().length;
        }

        @Override
        public Object getItem(int position) {
            return WorkoutParameter.values()[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_parameter_item, parent, false);
            }

            final WorkoutParameter workoutParameter = (WorkoutParameter) getItem(position);

            ImageView icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            final TextView title = (TextView) convertView.findViewById(R.id.tv_title);
            EditText value = (EditText) convertView.findViewById(R.id.et_value);

            value.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    values.put(workoutParameter, s.toString());
                }
            });

            icon.setImageResource(workoutParameter.getIcon());
            title.setText(workoutParameter.getTitle());

            return convertView;
        }
    }
}
