package br.com.pechinchadehoje.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.pechinchadehoje.fragment.MenorPrecoFragment;
import br.com.pechinchadehoje.fragment.ProximoFragment;

/**
 * Created by Anderson Uchoa on 15/11/16.
 */

public class MainTabAdapter extends FragmentPagerAdapter {

    private Context context;
    private static final int QTD_TABS = 2;

    private static final String[] tabTitles= {"PRÓXIMO","MENOR PREÇO"};

    public MainTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new ProximoFragment();
        }else{
            return new MenorPrecoFragment();
        }

    }

    @Override
    public int getCount() {
        return QTD_TABS;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
