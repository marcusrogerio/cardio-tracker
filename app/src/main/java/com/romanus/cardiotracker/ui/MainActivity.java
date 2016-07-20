package com.romanus.cardiotracker.ui;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.romanus.cardiotracker.R;
import com.romanus.cardiotracker.ui.history.HistoryFragment;
import com.romanus.cardiotracker.ui.workout.WorkoutFragment;
import com.romanus.cardiotracker.ui.zones.HRZonesDetialsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tl_tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewPager.setAdapter(new CTFragmentPageAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);
    }

    class CTFragmentPageAdapter extends FragmentPagerAdapter {

        private final int PAGES_COUNT = 3;
        private int tabTitles[] = new int[] { R.string.workout, R.string.history, R.string.hr_details};
        private Context context;

        public CTFragmentPageAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return context.getResources().getString(tabTitles[position]);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position) {
                case 0 : {
                    fragment = WorkoutFragment.newInstance();
                } break;
                case 1 : {
                    fragment = HistoryFragment.newInstance();
                } break;
                case 2 : {
                    fragment = HRZonesDetialsFragment.newInstance();
                } break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return PAGES_COUNT;
        }
    }
}
