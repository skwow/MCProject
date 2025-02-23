package com.example.skwow.mcproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private static final String ARG_PAGE = "arg_page";
    public static final String TAG = "MainFragment";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CustomPagerAdapter customPagerAdapter;
    private FloatingActionButton fabPlus, fabCreateEvent;
    private Animation FabOpen, FabClose, FabRotationClockwise, FabRotationAntiClockwise;
    private LinearLayout fab2_box;
    PageFragment pageFragment = new PageFragment();
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private boolean Fab_isOpen;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(int pageNumber) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fab_isOpen = false;
        Log.d(TAG, "Main Fragment called from ft.replace()");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        fabPlus = (FloatingActionButton) rootView.findViewById(R.id.fab_plus);
        fabCreateEvent = (FloatingActionButton) rootView.findViewById(R.id.fab_createEvent);
        fab2_box = (LinearLayout) rootView.findViewById(R.id.fab2_box);

        FabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        FabRotationClockwise = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_clockwise);
        FabRotationAntiClockwise = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anticlockwise);

        fragmentArrayList.add(new EventFragment());
        //fragmentArrayList.add(new TradeFragment());
//        fragmentArrayList.add(new SportsFragment());
//        fragmentArrayList.add(new EntertainmentFragment());
//        fragmentArrayList.add(new TripFragment());

        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( Fab_isOpen ) {
                    fab2_box.startAnimation(FabClose);
                    fabCreateEvent.startAnimation(FabClose);
                    fabPlus.startAnimation(FabRotationAntiClockwise);
                    fab2_box.setVisibility(View.INVISIBLE);
                    fabCreateEvent.setClickable(false);
                    Fab_isOpen = false;
                }
                else {
                    fab2_box.startAnimation(FabOpen);
                    fabCreateEvent.startAnimation(FabOpen);
                    fabPlus.startAnimation(FabRotationClockwise);
                    fabCreateEvent.setClickable(true);
                    fab2_box.setVisibility(View.VISIBLE);
                    Fab_isOpen = true;
                }
            }
        });

        fabCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab2_box.startAnimation(FabClose);
                fabCreateEvent.startAnimation(FabClose);
                fabPlus.startAnimation(FabRotationAntiClockwise);
                fab2_box.setVisibility(View.INVISIBLE);
                fabCreateEvent.setClickable(false);
                Fab_isOpen = false;
                EventFragment.toggleVisiblity();
            }
        });

        customPagerAdapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager(), fragmentArrayList);

        viewPager.setAdapter(customPagerAdapter);
        tabLayout.setTabsFromPagerAdapter(customPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return rootView;
    }
}