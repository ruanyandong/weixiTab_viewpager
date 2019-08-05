package com.ai.viewpagertab.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ai.viewpagertab.MainActivity;
import com.ai.viewpagertab.R;
import com.ai.viewpagertab.utils.L;

public class TabFragment extends Fragment {

    private static final String BUNDLE_KEY_TITLE = "key_title";

    private TextView mTvTitle;
    private String mTitle;

    public static interface OnTitleClickListener{
        void onClick(String title);
    }

    private OnTitleClickListener mListener;

    public void setOnTitleClickListener(OnTitleClickListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 外部向fragment传值
     * @param title
     * @return
     */
    public static TabFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE,title);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);//传值关键
        return fragment;
    }

    /**
     * 获取外部传给fragment的值
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null){
            mTitle = arguments.getString(BUNDLE_KEY_TITLE,"");
        }
        L.d("onCreate , title = "+mTitle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.d("onCreateView , title = "+mTitle);
        return inflater.inflate(R.layout.fragment_tab,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);

        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取Activity对象
                // 写法1：
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null){
                    mainActivity.changeWeChatTab(" 微信changed！");
                }
                // 写法2：
//                Activity activity = getActivity();
//                if (activity instanceof MainActivity){
//                    ((MainActivity)activity).changeWeChatTab("");
//                }
                // 写法3：
                //问题在于：我们Fragment会触发一些事件，Activity去响应这些事件
                if (mListener != null){
                    mListener.onClick("微信changed！");
                }


            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.d("onDestroyView, title = "+mTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d("onDestroy , title = "+mTitle);
    }

    // 暴露给外部使用
    public void changeTitle(String title){
        // 注意Fragment的View创建以后才能设置
        if (!isAdded()){
            return;
        }
        mTvTitle.setText(title);
    }

}
