# GamePlane
**通过自定义View实现的Android微信打飞机游戏，支持API Level 8+，如果觉得不错，欢迎大家Star和Fork！**

游戏说明
----

 1. 飞机一直发射子弹，用手指滑动可以改变飞机的位置
 2. 不同的敌机抗击打能力不同，当敌机被击中一定子弹数量时会爆炸，爆炸有动画效果
 3. 每隔一段时间都会有双发子弹或炸弹等道具奖励
 4. 获得双发子弹之后，子弹变为双发
 5. 获得炸弹道具之后，可以通过双击将屏幕内的所有敌机炸毁

 ![这里写图片描述](https://github.com/iSpring/GamePlane/blob/master/game.png)

 ![这里写图片描述](https://github.com/iSpring/GamePlane/blob/master/screenshot.gif)


实现
--

 - 我们定义了`Sprite`类，即精灵类，游戏中的飞机、子弹、奖励道具等都是继承自该类，我们通过moveTo()、move()等方法控制精灵的位置，通过beforeDraw()、onDraw()、afterDraw()实现相应的绘图逻辑。精灵类及其子类继承如下所示：
 
  ![这里写图片描述](https://github.com/iSpring/GamePlane/blob/master/sprite.png)

 - GameView是我们自定义的View类，主要重写了`onDraw()`和`onTouchEvent()`方法。onDraw()源码如下所示：

	```
	@Override
	protected void onDraw(Canvas canvas) {
	    //我们在每一帧都检测是否满足延迟触发单击事件的条件
	    if(isSingleClick()){
	        onSingleClick(touchX, touchY);
	    }
	
	    super.onDraw(canvas);
	
	    if(status == STATUS_GAME_STARTED){
	        drawGameStarted(canvas);
	    }else if(status == STATUS_GAME_PAUSED){
	        drawGamePaused(canvas);
	    }else if(status == STATUS_GAME_OVER){
	        drawGameOver(canvas);
	    }
	}
	```

    在某一时刻GameView有三种状态：游戏开始STATUS_GAME_STARTED、游戏暂停STATUS_GAME_PAUSED和游戏结束STATUS_GAME_OVER。在不同的状态下我们会调用不同的绘制方法，这几个方法中都会调用方法`postInvalidate()`，这样驱动着View不断重绘，进而不断调用`onDraw()`方法实现游戏的动态效果。关于绘图技巧，可以参见另一篇博文[《Android中Canvas绘图基础详解（附源码下载）》](http://blog.csdn.net/iispring/article/details/49770651)。
 
 - 我们也重写了GameView的`onTouchEvent()`方法。由于View只支持单击事件，而不支持双击事件，所以我们自己定义了一个`resolveTouchType()`方法，通过这个方法可以合成我们自己想要的事件类型，比如双击事件。我们记录`MotionEvent.ACTION_DOWN`和`MotionEvent.ACTION_UP`的时间，一次单击事件由ACTION_DOWN和ACTION_UP两个事件合成，假设从ACTION_DOWN到ACTION_UP间隔小于200毫秒，我们就认为发生了一次单击事件。一次双击事件由两个点击事件合成，两个单击事件之间小于300毫秒，我们就认为发生了一次双击事件。在触发了双击事件的时候，我们就会触发炸弹，将屏幕内的敌机都炸毁。当处于ACTION_MOVE状态时，我们就通过`event.getX()`和`event.getY()`改变战斗机的位置。关于`MotionEvent`的详细信息可以参另一篇博文[《Android中TouchEvent触摸事件机制》](http://blog.csdn.net/iispring/article/details/50364126)。
 
 - 我们还为GameView提供了start()、pause()、resume()和destroy()等方法，使其具备类似于Activity的生命周期，方便在Activity中对GameView进行状态管理。
 
 - 小敌机类，体积小，抗打击能力低；中敌机类，体积中等，抗打击能力中等；大敌机类，体积大，抗打击能力强。当敌机销毁的时候，我们使用了爆炸效果，使用了如下的图片：
  ![这里写图片描述](http://img.blog.csdn.net/20160724162747852)
  
  这张图片演示了爆炸从开始到结束14个阶段的效果图，我们用两帧绘制爆炸的一个阶段，这样完整绘制一个爆炸效果需要28帧，在绘制完最后一个阶段之后，`Explosion`类会销毁自己。
   

**如果觉得不错，欢迎大家Star和Fork！**
