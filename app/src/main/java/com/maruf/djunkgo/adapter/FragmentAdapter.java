package com.maruf.djunkgo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maruf.djunkgo.frag_all;
import com.maruf.djunkgo.frag_anorganik;
import com.maruf.djunkgo.frag_organik;
import com.maruf.djunkgo.frag_sold;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1 :
                return new frag_organik();
            case 2:
                return new frag_anorganik();
            case 3 :
                return new frag_sold();
        }
        return new frag_all();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
