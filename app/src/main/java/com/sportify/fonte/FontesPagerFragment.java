package com.sportify.fonte;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.sportify.R;
import com.sportify.enums.DisplayType;
import com.sportify.programs.ProgramRunnerFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class FontesPagerFragment extends Fragment {
    FragmentPagerItemAdapter pagerAdapter = null;
    ViewPager mViewPager = null;
    public static FontesPagerFragment newInstance(String name, int id) {
        FontesPagerFragment f = new FontesPagerFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putInt("id", id);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pager, container, false);
        mViewPager = view.findViewById(R.id.pager);

        if (mViewPager.getAdapter() == null) {
            Bundle freeWorkoutArgs = new Bundle();
            freeWorkoutArgs.putInt("displayType", DisplayType.FREE_WORKOUT_DISPLAY.ordinal());
            freeWorkoutArgs.putLong("templateId", -1);

            Bundle guidedWorkoutArgs = new Bundle();
            guidedWorkoutArgs.putInt("displayType", DisplayType.PROGRAM_RUNNING_DISPLAY.ordinal());
            guidedWorkoutArgs.putLong("templateId", -1);

            Bundle args = this.getArguments();
            args.putLong("machineID", -1);
            args.putLong("machineProfile", -1);

            pagerAdapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(this.getContext())
                .add(R.string.program, ProgramRunnerFragment.class, guidedWorkoutArgs)
                .add(R.string.GraphLabel, FonteGraphFragment.class, args)
                .add(R.string.HistoryLabel, FonteHistoryFragment.class, args)
                .create());

            mViewPager.setAdapter(pagerAdapter);

            SmartTabLayout viewPagerTab = view.findViewById(R.id.viewpagertab);
            viewPagerTab.setViewPager(mViewPager);

            viewPagerTab.setOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Fragment frag1 = pagerAdapter.getPage(position);
                    if (frag1 != null)
                        frag1.onHiddenChanged(false);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
        return view;
    }

    public FragmentPagerItemAdapter getViewPagerAdapter() {
        return (FragmentPagerItemAdapter) ((ViewPager) (getView().findViewById(R.id.pager))).getAdapter();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {

            if (getViewPagerAdapter() != null) {
                Fragment frag1;
                for (int i = 0; i < 4; i++) {
                    frag1 = getViewPagerAdapter().getPage(i);
                    if (frag1 != null)
                        frag1.onHiddenChanged(false);
                }
            }
        }
    }
}
