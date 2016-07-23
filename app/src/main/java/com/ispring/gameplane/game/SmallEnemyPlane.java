package com.ispring.gameplane.game;

import android.graphics.Bitmap;

/**
 * 小敌机类，体积小，抗打击能力低
 */
public class SmallEnemyPlane extends EnemyPlane {

    public SmallEnemyPlane(Bitmap bitmap){
        super(bitmap);
        setPower(1);//小敌机抗抵抗能力为1，即一颗子弹就可以销毁小敌机
        setValue(1000);//销毁一个小敌机可以得1000分
    }

}