package com.ai.viewpagertab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ai.viewpagertab.fragment.SplashFragment;
import com.ai.viewpagertab.view.transformer.ScaleTransformer;

public class SplashActivity extends AppCompatActivity {

    private ViewPager mVpMain;
    private int[] mResIds = new int[]{
            R.drawable.guid_one,
            R.drawable.guid_two,
            R.drawable.guid_three,
            R.drawable.guid_four,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mVpMain = findViewById(R.id.vp_main);
        mVpMain.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return SplashFragment.newInstance(mResIds[i]);
            }

            @Override
            public int getCount() {
                return mResIds.length;
            }
        });
        mVpMain.setPageTransformer(true,new ScaleTransformer());
    }

}
