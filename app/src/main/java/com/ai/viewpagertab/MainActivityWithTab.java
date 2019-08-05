package com.ai.viewpagertab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.ai.viewpagertab.fragment.TabFragment;
import com.ai.viewpagertab.utils.L;
import com.ai.viewpagertab.view.TabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityWithTab extends AppCompatActivity {

    private ViewPager mVpMain;
    private List<String> mTitles = new ArrayList<>(Arrays.asList("微信","通讯录","发现","我"));

    private TabView mTabWeChat;
    private TabView mTabFriend;
    private TabView mTabFind;
    private TabView mTabMine;

    private SparseArray<TabFragment> mFragments = new SparseArray<>();
    private List<TabView> mTabs = new ArrayList<>();

    private static final String BUNDLE_KEY_POS = "bundle_key_pos";
    private int mCurTabPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        if (savedInstanceState != null){
            mCurTabPos = savedInstanceState.getInt(BUNDLE_KEY_POS,0);
        }
        initViews();
        initViewPagerAdapter();
        initEvent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_KEY_POS,mVpMain.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    private void initEvent() {
        for (int i = 0; i < mTabs.size(); i++) {
           TabView tabView = mTabs.get(i);
           final int finalI = i;
           tabView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mVpMain.setCurrentItem(finalI,false);
                   setCurrentTab(finalI);
               }
           });
        }
    }

    private void initViewPagerAdapter() {
        //设置0和1效果一样,
        mVpMain.setOffscreenPageLimit(3);
        /**
         * 在Android Studio（包括所有JetBrains的IDE），使用Ctrl Shift +或-，就可以展开或收起全部代码。
         * Ctrl + 或 - 对当前方法展开或者收起。
         */
        mVpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {//  相当于createFragment
                TabFragment tabFragment = TabFragment.newInstance(mTitles.get(i));

                return tabFragment;
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment fragment = (TabFragment) super.instantiateItem(container, position);
                mFragments.put(position,fragment);
                return fragment;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                // 超出缓存范围的fragment就会调用这个方法
                mFragments.remove(position);
                super.destroyItem(container, position, object);
            }
        });
        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                L.d("onPageScrolled position = "+position+", positionOffset = "+positionOffset+", positionOffsetPixels = "+positionOffsetPixels);
                //  左->右 0->1，left pos，right pos + 1，positionOffset 0~1 ，positionOffsetPixels 0~屏幕宽度
                // left progress：1~0(1-positionOffset)；right progress：0~1(positionOffset)；

                //  右->左 1->0，left pos，right pos + 1，positionOffset 1~0 ，positionOffsetPixels 屏幕宽度~0
                // left progress：0~1(1-positionOffset)；right progress：1~0(positionOffset)；

                // left tab
                // right tab
                if (positionOffset > 0){
                    TabView left = mTabs.get(position);
                    TabView right = mTabs.get(position + 1);
                    left.setProgress(1-positionOffset);
                    right.setProgress(positionOffset);
                }

            }

            @Override
            public void onPageSelected(int position) {
                L.d("onPageSelected position = "+position);
            }

            /**
             * 有三种状态
             *  IDLE:完全停滞 0
             *  DRAGGING：手拽着拖动 1
             *  SETTING: 手放开的状态 2
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                L.d("onPageScrollStateChanged state = "+state);
            }
        });
    }

    private void initViews() {
        mVpMain =findViewById(R.id.vp_main);
        mTabWeChat = findViewById(R.id.tab_wechat);
        mTabFriend = findViewById(R.id.tab_friend);
        mTabFind = findViewById(R.id.tab_find);
        mTabMine = findViewById(R.id.tab_mine);

        mTabWeChat.setIconAndText(R.drawable.eagle_vote_blue_fingerprint,R.drawable.eagle_vote_red_fingerprint,"微信");
        mTabFriend.setIconAndText(R.drawable.eagle_vote_blue_fingerprint,R.drawable.eagle_vote_red_fingerprint,"通讯录");
        mTabFind.setIconAndText(R.drawable.eagle_vote_blue_fingerprint,R.drawable.eagle_vote_red_fingerprint,"发现");
        mTabMine.setIconAndText(R.drawable.eagle_vote_blue_fingerprint,R.drawable.eagle_vote_red_fingerprint,"我");

        mTabs.add(mTabWeChat);
        mTabs.add(mTabFriend);
        mTabs.add(mTabFind);
        mTabs.add(mTabMine);

        // 开始默认选中第一个,如果是旋转屏幕后，重建Activity后默认选中之前被选中的tab
        setCurrentTab(mCurTabPos);
    }

    private void setCurrentTab(int position){
        for (int i = 0; i < mTabs.size(); i++) {
            TabView tabView = mTabs.get(i);
            if (i == position){
                tabView.setProgress(1);
            }else {
                tabView.setProgress(0);
            }
        }
    }

}
