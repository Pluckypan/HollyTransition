# HollyTransition

### 零、导读
深入解读Android过渡动画Transition:
1. **页面切换动画(过场动画)**
2. **共享元素动画**
3. **延时动画**
4. **场景动画**

### 一、Transition前世今生
> 为了支持各种交互视觉设计的不断更新，Android对于开发者提供了越来越多的动画API支持。从API 1就存在的Drawable Animation和View Animation，以及API 11(Android 3.0)以后加入的Property Animation。而过渡动画Transition是在API 19(Android 4.4.2)中加入的。说道炫酷的动画，很多人首先想到的是Android5.0开发流行起来的共享元素动画。然而共享元素动画只是Transition的冰山一角。今天就让我们来一一揭开。

### 二、为什么要引入Transition
> 那为什么要引入Transition动画呢？由于在Android引入了Metrial Desigon之后，动画的场面越来越大，比如以前我们制作一个动画可能涉及到的View就一个，或者就那么几个，如果我们一个动画中涉及到了当前Activity视图树中的各个View，那么情况就复杂了。比如我们要一次针对视图树中的10个View进行动画，这些View的效果都不同，可能有的是平移，有的是旋转，有的是淡入淡出，那么不管是使用之前哪种方式的动画，我们都需要为每个View定义一个开始状态和结束状态【关键帧，比如放缩，我们得设置fromXScale和toXScale 】，随着View个数的增加，这个情况会越来越复杂。这个时候如果使用一堆Animator去实现这一连串动画，代码将会又臭又长，Transition的出现大大减轻了开发的工作。

### 三、页面切换动画(过场动画)
> 以前使用过场动画时，实现共享元素，主要是使用Activity，但是有遇到很多坑：比如最严重的一个是在某些机型上出现做返场动画时，第二个页面的画面还残留在页面上；第二个问题是，在存在虚拟导航栏的手机上，由于不同Activity是在不同的Window上绘制的，在Activity切换时界面存在闪烁的情况。而Fragment的切换则不存在这样的问题。综合考虑，决定使用Fragment。

#### 示例图1：入场动画
![入场动画](images/transition_in.gif)

#### 示例图2：返场动画
![返场动画](images/transition_out.gif)

#### 三(1)、共享元素动画
> 如上图中 **画廊按钮**、**拍摄按钮**、**设置按钮** ;页面切换时，对共享元素动画来讲，重要的函数有以下几个。共享元素执行的动画主要是针对将要进入的页面的。一个是入场动画 EnterTransition,另一个是返场 ReturnTransition。在做共享元素动画时，需要在前后两个页面对共享元素设定TransitionName，且对应的两个View，TransitionName需要相同。

```
//在第二个页面(即要进入的那个页面)设置共享元素TransitionName  ViewCompat可兼容5.0以下版本，不报错
ViewCompat.setTransitionName(mFakeLeft, SHARE_NAME_LEFT);
ViewCompat.setTransitionName(mPhotoBtn, SHARE_NAME_MIDDLE);
ViewCompat.setTransitionName(mFakeRight, SHARE_NAME_RIGHT);

//设定入场动画和返场动画
fragment.setSharedElementEnterTransition(new ChangeBounds());
fragment.setSharedElementReturnTransition(new ChangeBounds());

//添加共享元素
FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
fragmentTransaction.replace(R.id.app_ctrl_view, fragment);
fragmentTransaction.addToBackStack(null);
if (shareViews != null && shareViews.length > 0) {
    for (Pair<View, String> shareView : shareViews) {
        fragmentTransaction.addSharedElement(shareView.first, shareView.second);
    }
}
fragmentTransaction.commit();
```

##### 疑问：上图的共享元素是如何实现形变的过程中发生alpha变化的？
> 刚开始，准备直接使用 xml 定制动画，一个changeBounds(左中右三个按钮同时形变) + 一个 fade (左右按钮在形变的同时 fadeout),但是发现只有形变生效。于是尝试使用自定义ChangeBounds来改造共享元素动画，在createAnimator中拿到Animator，从而可以拿到Animator变化的过程 `ValueAnimator.AnimatorUpdateListener`,这样就可以实现形变的同时，发生alpha的变化。 [示例代码](https://github.com/Pluckypan/HollyTransition/blob/master/app/src/main/java/engineer/echo/transition/cmpts/widget/transition/BoundsAndAlpha.java)

```
// 改造需要了解Transition的三大核心方法如下。
@Override
public void captureStartValues(TransitionValues transitionValues) {
    super.captureStartValues(transitionValues);
    Log.d(TAG, "captureStartValues: transitionValues=" + transitionValues);
}

@Override
public void captureEndValues(TransitionValues transitionValues) {
    super.captureEndValues(transitionValues);
    Log.d(TAG, "captureEndValues: transitionValues=" + transitionValues);
}

@Override
public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
    return super.createAnimator(sceneRoot, startValues, endValues);
}
```

#### 三(2)、内容动画
> 如上面图片中，设置页面的**背景渐变**、**动起来 SlideUp**、**功能说明 SlideUp**;拍摄页面的**进度条封面 Slide Left&Right**。这里所说的`内容动画`，指的是Fragment布局中除共享元素外的View执行的动画，主要是为了区分`共享元素动画`。对内容过场动画比较重要的几个函数,下面这种图比较形象地描述这个关系。Activity和Fragment的意思是一样的。这里以Activity的示意图来说明。

##### 示例图3：入场动画对应关系
![入场动画](images/transition-0.png)

##### 示例图4：返场动画对应关系
![返场动画](images/transition-1.png)

``` java
TransitionSet transitionSet=new TransitionSet();
//从 A Fragment 进入 B Fragment,A会执行 ExitTransition,B 会执行EnterTransition
fragment.setEnterTransition(transitionSet);
fragment.setExitTransition(transitionSet);
//按返回键时，B Fragment 会pop出栈，执行ReturnTransition，此时 A Fragment 重新回到栈顶，执行ReEnterTransition.
fragment.setReenterTransition(transitionSet);
fragment.setReturnTransition(transitionSet);
```

### 四、延时动画(TransitionManager.beginDelayedTransition)
#### 四(1)、进度条平滑过渡
> iOS进度条有个方法：- (void)setProgress:(float)progress animated:(BOOL)animated; 如果animated=true 那么，设置进度时，会从当前进度平滑过渡到目标进度，大大提升了用户体验。Android进度条并不具备这种功能，只能设定一个progress,控件就僵硬地跳到对应的位置。但是借助Transition，可实现这个功能。 [示例代码](https://github.com/Pluckypan/HollyTransition/blob/master/app/src/main/java/engineer/echo/transition/cmpts/widget/transition/ProgressTransition.java)

##### 示例图5：实现进度条平滑过渡
![进度条](images/progress.gif)

#### 四(2)、放大缩小效果
##### 示例图6：实现同一ViewGroup中两个View之间协调放大与缩小效果
![放大缩小](images/scale.gif)

#### 四(3)、Slide与Fade组合动画
##### 示例图7：从上滑入从下滑出并且渐入渐出
![渐入渐出](images/slide_fade.gif)


### 五、路径动画
> 路径动画在日常APP中使用的场景很多，比如京东、天猫 添加商品至购物车的动画。实现起来非常简单。直接使用Transition.setPathMotion(new ArcMotion());然后结合延时动画TransitionManager.beginDelayedTransition()即可。

##### 示例图片8：普通路径动画
![路径动画-普通](images/path_normal.gif)
##### 示例图片9：路径动画在共享元素中的运用
![路径动画-普通](images/path_share_element.gif)

### 六、源码解析
> 入场动画时，是直接拿着 进入页面的View进行动画的，返场动画时，不直接拿着View进行动画，而是在Overlap上，创建与Targets对应的ImageView，然后截取Targets的画面，显示在ImageView上，返场动画主要是在Overlap上进行的。

``` java
    public Animator onDisappear(ViewGroup sceneRoot,
                                TransitionValues startValues, int startVisibility,
                                TransitionValues endValues, int endVisibility) {
        if ((mMode & MODE_OUT) != MODE_OUT) {
            return null;
        }

        View startView = (startValues != null) ? startValues.view : null;
        View endView = (endValues != null) ? endValues.view : null;
        /**
         * 说明几点：
         * #1 onDisappear方法位于android.transition.Visibility类中
         * #2 在返场动画时，场景中每个被添加进Transition的Target都会执行该方法
         * #3 这里定义了一个overlayView，先标记下，后面会用得到
         */
        View overlayView = null;
        View viewToKeep = null;
        if (endView == null || endView.getParent() == null) {
            if (endView != null) {
                // endView was removed from its parent - add it to the overlay
                overlayView = endView;
            } else if (startView != null) {
                // endView does not exist. Use startView only under certain
                // conditions, because placing a view in an overlay necessitates
                // it being removed from its current parent
                if (startView.getParent() == null) {
                    // no parent - safe to use
                    overlayView = startView;
                } else if (startView.getParent() instanceof View) {
                    /**
                     * 如果StartView存在父布局
                     */
                    View startParent = (View) startView.getParent();
                    TransitionValues startParentValues = getTransitionValues(startParent, true);
                    TransitionValues endParentValues = getMatchedTransitionValues(startParent,
                            true);
                    VisibilityInfo parentVisibilityInfo =
                            getVisibilityChangeInfo(startParentValues, endParentValues);
                    if (!parentVisibilityInfo.visibilityChange) {
                        /**
                         * 如果StartView父布局在动画过程中未参与Visibility变化的话，那么就会
                         * 创建一个ImageView，并将StartView 画布Canvas上的内容转换为Bitmap设置到新创建的ImageView上
                         */
                        overlayView = TransitionUtils.copyViewImage(sceneRoot, startView,
                                startParent);
                    } else if (startParent.getParent() == null) {
                        int id = startParent.getId();
                        if (id != View.NO_ID && sceneRoot.findViewById(id) != null
                                && mCanRemoveViews) {
                            // no parent, but its parent is unparented  but the parent
                            // hierarchy has been replaced by a new hierarchy with the same id
                            // and it is safe to un-parent startView
                            overlayView = startView;
                        }
                    }
                }
            }
        } else {
            // visibility change
            if (endVisibility == View.INVISIBLE) {
                viewToKeep = endView;
            } else {
                // Becoming GONE
                if (startView == endView) {
                    viewToKeep = endView;
                } else {
                    overlayView = startView;
                }
            }
        }
        final int finalVisibility = endVisibility;
        final ViewGroup finalSceneRoot = sceneRoot;

        if (overlayView != null) {
            // TODO: Need to do this for general case of adding to overlay
            int[] screenLoc = (int[]) startValues.values.get(PROPNAME_SCREEN_LOCATION);
            int screenX = screenLoc[0];
            int screenY = screenLoc[1];
            int[] loc = new int[2];
            sceneRoot.getLocationOnScreen(loc);
            overlayView.offsetLeftAndRight((screenX - loc[0]) - overlayView.getLeft());
            overlayView.offsetTopAndBottom((screenY - loc[1]) - overlayView.getTop());
            /**
             * 通过上面一系列的判断，最终会将得到的overlayView添加到 场景一（及A页面）根部局的Overlap上
             * 也就是说，其实在做返场动画时，所有的动画都是在 A页面的Overlap上进行的。做完动画再将其从Overlap上移除。
             * 这里的overlayView可能是 B页面的控件，也有可能是B页面控件的画面（new了一个ImageView的形式展示）
             * 什么是Overlap,可参考文章：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0130/2384.html
             */
            sceneRoot.getOverlay().add(overlayView);
            Animator animator = onDisappear(sceneRoot, overlayView, startValues, endValues);
            if (animator == null) {
                sceneRoot.getOverlay().remove(overlayView);
            } else {
                final View finalOverlayView = overlayView;
                addListener(new TransitionListenerAdapter() {
                    @Override
                    public void onTransitionEnd(Transition transition) {
                        finalSceneRoot.getOverlay().remove(finalOverlayView);
                    }
                });
            }
            return animator;
        }

        if (viewToKeep != null) {
            int originalVisibility = viewToKeep.getVisibility();
            viewToKeep.setTransitionVisibility(View.VISIBLE);
            Animator animator = onDisappear(sceneRoot, viewToKeep, startValues, endValues);
            if (animator != null) {
                DisappearListener disappearListener = new DisappearListener(viewToKeep,
                        finalVisibility, mSuppressLayout);
                animator.addListener(disappearListener);
                animator.addPauseListener(disappearListener);
                addListener(disappearListener);
            } else {
                viewToKeep.setTransitionVisibility(originalVisibility);
            }
            return animator;
        }
        return null;
    }

```

### 七、遇到的问题
1. Scene切换时会RemoveAllView 导致之前设置点击事件消失
2. 退场动画时，会截取View的canvas，并在A页面rootview的overlap上添加ImageView,如果自定义控件有影响canvas的绘制过程，则添加到ImageView上的Bitmap可能不正确
3. 共享元素必须是RootView的直接子View
4. 做页面间的过渡动画时，两个页面尽量简单，比如加载网络图片时，可以先使用前一个页面的图片做动画，等动画做完后再去加载图片。不可以一边儿做动画，一边儿加载图片，会造成动画很闪烁。
5. Fragment设置TransitionOverlap无效，还需要研究下。
``` java
fragment.setAllowEnterTransitionOverlap(false);
fragment.setAllowReturnTransitionOverlap(false);
```
6. 在Fragment中使用 **共享元素动画** 时，需要两个Fragment基于同一个`layout_id`,然后通过`replace`的形式打开。

### 八、TODO
- [ ] 图片切换平滑过渡效果
- [ ] 颜色变化平滑过渡
- [ ] 文字变化平滑过渡

### 九、参考项目
- [Transitions-Everywhere](https://github.com/andkulikov/Transitions-Everywhere)
> Transitions-Everywhere 可支持Transiton动画到 Android 4.O ,并且兼容 Android 2.2 +（无动画但保证运行）.

- [Material-Animations](https://github.com/lgvalle/Material-Animations)
> 详细解读Fragment与Fragment Activity与Activity Activity与Fragment之间的切换动画

- [TransitionExample](https://github.com/WakeHao/TransitionExample)
> 早在Android 4.4，Transition 就已经引入，但在5.0才得以真正的实现。而究竟Transition是用来干嘛的呢?这个Demo可以带你熟悉基本的操作。特点是比较熟练的使用xml来定义动画，动画衔接做得比较好。

- [Android-Material-Examples](https://github.com/saulmm/Android-Material-Examples)
> 主要特点是介绍ViewPager中实现的一些Transition动画

- [ViewOverlay与animation介绍](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0130/2384.html)
> ViewOverlay与animation介绍。Transition在ReturnTransition动画时，会在第一个页面rootView的overlap上添加ImageView来执行动画。这篇文章详细介绍了Android中ViewOverlay的用处和原理。

