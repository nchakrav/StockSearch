package com.nikhilchakravartula.stocksearch.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nikhilchakravartula.stocksearch.fragments.HistoricalGraphFragment;
import com.nikhilchakravartula.stocksearch.fragments.LineGraphFragment;

public class GraphPagerAdapter extends FragmentStateAdapter {

    Bundle fragment_bundle;
    HistoricalGraphFragment historical;
    LineGraphFragment line;

    public GraphPagerAdapter(FragmentActivity fa, Bundle fragment_bundle) {
        super(fa);
        line = LineGraphFragment.newInstance(fragment_bundle.getString(LineGraphFragment.TICKER_KEY), fragment_bundle.getInt(LineGraphFragment.TIMESTAMP_KEY), fragment_bundle.getString(LineGraphFragment.COLOR_KEY));
        historical = HistoricalGraphFragment.newInstance(fragment_bundle.getString(HistoricalGraphFragment.TICKER_KEY), fragment_bundle.getString(HistoricalGraphFragment.COLOR_KEY));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) return line;
        return historical;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
