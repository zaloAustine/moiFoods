package com.example.moifoods.Adapters;





import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.moifoods.fragments.MyCart;
import com.example.moifoods.fragments.MyOrders;


public class PageAdapter extends FragmentPagerAdapter {




    public PageAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0 :
                return  new MyCart();
            case 1:
                return  new MyOrders();
            default:
                return  new MyCart();

        }


    }

    @Override
    public int getCount() {
        return 2;
    }
}
