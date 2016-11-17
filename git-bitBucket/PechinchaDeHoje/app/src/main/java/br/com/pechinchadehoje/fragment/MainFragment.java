package br.com.pechinchadehoje.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.pechinchadehoje.R;
import br.com.pechinchadehoje.adapter.MainTabAdapter;

public class MainFragment extends BaseFragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MainTabAdapter mainTabAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewPagerTabs);
        viewPager.setOffscreenPageLimit(1);

        mainTabAdapter = new MainTabAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(mainTabAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        int corUnselected = getContext().getResources().getColor(R.color.colorPrimaryDark);
        int corSelected = getContext().getResources().getColor(R.color.white);
        tabLayout.setTabTextColors(corUnselected, corSelected);

        tabLayout.setupWithViewPager(viewPager);

        Log.i("TESTE_MAIN_FRAGMENT", "onCreateView");
        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("TESTE_MAIN_FRAGMENT", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("TESTE_MAIN_FRAGMENT", "onResume");
    }

}
