package com.romanus.cardiotracker.ui.zones;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romanus.cardiotracker.R;

/**
 * Created by rursu on 20.07.16.
 */
public class HRZonesDetialsFragment extends Fragment {

    public static HRZonesDetialsFragment newInstance() {

        Bundle args = new Bundle();
        
        HRZonesDetialsFragment fragment = new HRZonesDetialsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zone_details, container, false);
        return view;
    }
}
