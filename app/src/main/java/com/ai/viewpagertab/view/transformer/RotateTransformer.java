package com.ai.viewpagertab.view.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class RotateTransformer implements ViewPager.PageTransformer {

    private static final int MAX_ROTATE = 15;

    @Override
    public void transformPage(@NonNull View view, float position) {

        // a->b
        // a,position:(0,-1)
        // b,position:(1,0)

        // b->a
        // a,position:(-1,0)
        // b,position:(0,1)

        if (position < -1){// [负无穷，-1]
            // a滑动出界面了
            view.setRotation(-MAX_ROTATE);
            view.setPivotY(view.getHeight());
            view.setPivotX(view.getWidth());
        }else if (position <= 1){// [-1,1]

            //  左边这个页面
            if (position < 0){
                // a->b,a:position:(0,-1)
                view.setPivotY(view.getHeight());
                // 0.5w,w
                view.setPivotX(0.5f*view.getWidth()+0.5f*(-position)*view.getWidth());
                //0->-max
                view.setRotation(MAX_ROTATE*position);
                // b->a ,a:position:(-1,0)

            }else {// 右边这个页面
                // a->b
                // b,position:(1,0)
                view.setPivotY(view.getHeight());
                view.setPivotX(view.getWidth()*0.5f*(1-position));
                // MAX,0
                view.setRotation(MAX_ROTATE*position);
                // b->a
                // b,position:(0,1)
            }

        }else{// [1,正无穷]
            // b滑动出界面了
            view.setRotation(MAX_ROTATE);
            view.setPivotY(view.getHeight());
            view.setPivotX(0);
        }

    }
}
