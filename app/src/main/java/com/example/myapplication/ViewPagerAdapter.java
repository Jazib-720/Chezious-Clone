package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment1();  // First tab shows Fragment1
            case 1:
                return new Fragment2();  // Second tab shows Fragment2
            case 2:
                return new Fragment3();  // Third tab shows Fragment3
            case 3:
                return new Fragment4();  // Fourth tab shows Fragment4
            case 4:
                return new Fragment5();  // Fifth tab shows Fragment5
            case 5:
                return new Fragment6();  // Sixth tab shows Fragment6
            case 6:
                return new Fragment7();  // Seventh tab shows Fragment7
            case 7:
                return new Fragment8();  // Eighth tab shows Fragment8
            case 8:
                return new Fragment9();  // Ninth tab shows Fragment9
            case 9:
                return new Fragment10(); // Tenth tab shows Fragment10
            case 10:
                return new Fragment11(); // Eleventh tab shows Fragment11
            case 11:
                return new Fragment12(); // Twelfth tab shows Fragment12
            case 12:
                return new Fragment13(); // Thirteenth tab shows Fragment13
            default:
                return new Fragment1(); // If position doesn't match any case, return null
        }

    }

    @Override
    public int getItemCount() {
        return 13;  // Number of tabs you have
    }


}
