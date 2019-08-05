package com.ai.viewpagertab;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ai.viewpagertab.fragment.TabFragment;
import com.ai.viewpagertab.utils.L;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVpMain;
    private List<String> mTitles = new ArrayList<>(Arrays.asList("微信","通讯录","发现","我"));

    private Button mBtnWeChat;
    private Button mBtnFriend;
    private Button mBtnFind;
    private Button mBtnMine;

    private SparseArray<TabFragment> mFragments = new SparseArray<>();
    private List<Button> mTabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initViewPagerAdapter();
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
                if (i==0){
                    tabFragment.setOnTitleClickListener(new TabFragment.OnTitleClickListener() {
                        @Override
                        public void onClick(String title) {
                            changeWeChatTab(title);
                        }
                    });
                }

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
                    Button left = mTabs.get(position);
                    Button right = mTabs.get(position + 1);
                    left.setText((1-positionOffset)+"");
                    right.setText(positionOffset+"");
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
        mBtnWeChat = findViewById(R.id.btn_wechat);
        mBtnFriend = findViewById(R.id.btn_friend);
        mBtnFind = findViewById(R.id.btn_find);
        mBtnMine = findViewById(R.id.btn_mine);

        mTabs.add(mBtnWeChat);
        mTabs.add(mBtnFriend);
        mTabs.add(mBtnFind);
        mTabs.add(mBtnMine);

        mBtnWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取第一个fragment
                TabFragment fragment = mFragments.get(0);
                if (fragment != null){// 防止超出缓存区fragment被移除而获取不到
                    fragment.changeTitle("微信 changed！");
                }

            }
        });
    }

    public void changeWeChatTab(String title){
        mBtnWeChat.setText(title);
    }
}
