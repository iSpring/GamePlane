package com.ispring.gameplane.game;

import android.graphics.Bitmap;

/**
 * 子弹类，从下向上沿直线移动
 */
public class Bullet extends AutoSprite {

    public Bullet(Bitmap bitmap){
        super(bitmap);
        setSpeed(-10);//负数表示子弹向上飞
    }

}