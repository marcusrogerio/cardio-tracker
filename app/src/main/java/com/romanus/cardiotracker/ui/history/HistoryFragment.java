package com.romanus.cardiotracker.ui.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.romanus.cardiotracker.R;
import com.romanus.cardiotracker.util.WorkoutType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rursu on 20.07.16.
 */
public class HistoryFragment extends Fragment {

    @BindView(R.id.rv_history_list)
    RecyclerView historyList;

    public static HistoryFragment newInstance() {
        Bundle args = new Bundle();
        
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        initList();

        return view;
    }

    private void initList() {
        List<WorkoutHistoryItem> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            WorkoutHistoryItem historyItem = new WorkoutHistoryItem("Description" + i, "Date " + i, WorkoutType.RUNNING);
            list.add(historyItem);
        }

        historyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyList.setAdapter(new WorkoutHistoryAdapter(list));
    }

    class WorkoutHistoryItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_activity_icon)
        ImageView image;

        @BindView(R.id.tv_description)
        TextView description;

        @BindView(R.id.tv_date_time)
        TextView dateTime;


        public WorkoutHistoryItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryItemViewHolder> {

        private List<WorkoutHistoryItem> list;

        public WorkoutHistoryAdapter(List<WorkoutHistoryItem> list) {
            this.list = list;
        }

        @Override
        public WorkoutHistoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.history_item, parent, false);
            return new WorkoutHistoryItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(WorkoutHistoryItemViewHolder holder, int position) {
            WorkoutHistoryItem historyItem = list.get(position);

            holder.dateTime.setText(historyItem.getDate());
            holder.description.setText(historyItem.getDescription());
            holder.image.setImageResource(historyItem.getType().getIcon());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
