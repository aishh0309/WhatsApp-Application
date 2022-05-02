package com.aishh.aishh_whatsapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.aishh.aishh_whatsapp.Fragments.CallsFragment;
import com.aishh.aishh_whatsapp.Fragments.ChatFragment;
import com.aishh.aishh_whatsapp.Fragments.StatusFragment;

public class FragmentsAdapter extends FragmentStateAdapter {
    private String[] titles=new String[]{"CHATS","STATUS","CALLS"};
    public FragmentsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new ChatFragment();
            case 1: return new StatusFragment();
            case 2: return new CallsFragment();
        }
        return new ChatFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
