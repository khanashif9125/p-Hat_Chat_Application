package com.workforashif.basicchatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SetionPageAdapter extends FragmentPagerAdapter {
    public SetionPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ChatFragment chatFragment=new ChatFragment();
                return chatFragment;
            case 1:
                FriendsFragment friendsFragment=new FriendsFragment();
                return friendsFragment;
            case 2:
                RequestFragment requestFragment=new RequestFragment();
                return requestFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        switch (position){
            case 0:
                return "CHATS";
            case 1:
                return "FRIENDS";
            case 2:
                return "REQUEST";
            default:
                return null;
        }
    }
}