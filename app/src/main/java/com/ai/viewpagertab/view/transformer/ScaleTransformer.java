package com.ai.viewpagertab.view.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.ai.viewpagertab.utils.L;

public class ScaleTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(@NonNull View view, float position) {
        // 打日志找规律
        L.d("tag = "+view.getTag()+" pos = "+position);
        // a->b
        // a,position:(0,-1)
        // b,position:(1,0)

        // b->a
        // a,position:(-1,0)
        // b,position:(0,1)

        if (position < -1){// [负无穷，-1]
            // a滑动出界面了
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);

            view.setAlpha(MIN_ALPHA);
        }else if (position <= 1){// [-1,1]

            //  左边这个页面
            if (position < 0){
                // a->b,a:position:(0,-1)
                // [1,0.75f]
                float scaleA = MIN_SCALE+(1- MIN_SCALE)*(1+position);
                view.setScaleX(scaleA);
                view.setScaleY(scaleA);
                // b->a ,a:position:(-1,0)
                // [0.75f,1]
               // MIN_SCALE+(1-MIN_SCALE)*(1+position);

                // [1,0.5f]
                float alphaA = MIN_ALPHA + (1-MIN_ALPHA)*(1+position);
                view.setAlpha(alphaA);

                // [0.5f,1]
                // MIN_ALPHA + (1-MIN_ALPHA)*(1+position)

            }else {// 右边这个页面
                // a->b
                // b,position:(1,0)
                // [0.75f,1]
                float scaleB = MIN_SCALE+(1-MIN_SCALE)*(1-position);
                view.setScaleX(scaleB);
                view.setScaleY(scaleB);
                // b->a
                // b,position:(0,1)
                // [1,0.75f]
               // MIN_SCALE+(1-MIN_SCALE)*(1-position);

                // 和scale思路一样[0.5f,1]
               float alphaB = MIN_ALPHA + (1-MIN_ALPHA)*(1-position);
               view.setAlpha(alphaB);
               // [1,0.5f]
                // MIN_ALPHA + (1-MIN_ALPHA)*(1-position)
            }

        }else{// [1,正无穷]
            // b滑动出界面了
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);

            view.setAlpha(MIN_ALPHA);
        }
    }
}
