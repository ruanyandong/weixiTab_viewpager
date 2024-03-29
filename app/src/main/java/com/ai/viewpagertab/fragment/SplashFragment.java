package com.ai.viewpagertab.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.ai.viewpagertab.R;

public class SplashFragment extends Fragment {
    private ImageView mIvContent;
    private int mResId;
    private static final String BUNDLE_KEY_RES_ID = "bundle_key_res_id";

    public static SplashFragment newInstance(int resId) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_RES_ID,resId);
        Log.d("TAG", "newInstance: resId="+resId);
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            mResId = bundle.getInt(BUNDLE_KEY_RES_ID);
            Log.d("TAG", "onCreate: mResId="+mResId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIvContent = view.findViewById(R.id.iv_content);
        mIvContent.setImageResource(mResId);
        // 找规律使用
        mIvContent.setTag(mResId);
    }

}
