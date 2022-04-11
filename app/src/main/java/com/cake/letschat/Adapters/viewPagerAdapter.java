package com.cake.letschat.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cake.letschat.Fragment.cameraFrag;
import com.cake.letschat.Fragment.chatFrag;
import com.cake.letschat.Fragment.mainFrag;

public class viewPagerAdapter extends FragmentPagerAdapter {
    public viewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new cameraFrag();
            case 1:
                return new mainFrag();
            case 2:
                return new chatFrag();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
