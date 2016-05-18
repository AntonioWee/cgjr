package cgncjr.com.cgncjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cgncjr.com.cgncjr.R;
import cgncjr.com.cgncjr.fragment.ScreenSlidePageFragment;
import cgncjr.com.cgncjr.lib.ChangeLogHelper;
import cgncjr.com.cgncjr.utils.LogUtils;


/**
 * 引导页的滑动查看
 * Demonstrates a "screen-slide" animation using a {@link android.support.v4.view.ViewPager}. Because {@link android.support.v4.view.ViewPager}
 * automatically plays such an animation when calling {@link android.support.v4.view.ViewPager#setCurrentItem(int)}, there
 * isn't any animation-specific code in this sample.
 * <p/>
 * <p>This sample shows a "next" button that advances the user to the next step in a wizard,
 * animating the current screen out (to the left) and the next screen in (from the right). The
 * reverse animation is played when the user presses the "previous" button.</p>
 *
 * @see ScreenSlidePageFragment
 */
public class ScreenSlideActivity extends FragmentActivity {
    public static final String TAG = "ScreenSlideActivity";
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;

    public static final String SCREEN_SLIDE_ACTIVITY_INTENT_PARAM_CG_CHARACTERISTIC = "cg_characteristic";
    public static final String SCREEN_SLIDE_ACTIVITY_INTENT_PARAM_ISFIRSTOPEN = "isFirstOpen";
    //这个标志是用来干啥的？
    private boolean flag = false;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    @Bind(R.id.pager)
    protected ViewPager mPager;

    @Bind(R.id.toolbar_back_icon)
    protected ImageView mBack;

    protected boolean isFisrtOpen = false;


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    /**
     * Activity被系统杀死时被调用.
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死.
     * 另外,当跳转到其他Activity或者按Home键回到主屏时该方法也会被调用,系统是为了保存当前View组件的状态.
     * 在onPause之前被调用.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SCREEN_SLIDE_ACTIVITY_INTENT_PARAM_CG_CHARACTERISTIC, flag);
        outState.putBoolean(SCREEN_SLIDE_ACTIVITY_INTENT_PARAM_ISFIRSTOPEN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            flag = savedInstanceState.getBoolean(SCREEN_SLIDE_ACTIVITY_INTENT_PARAM_CG_CHARACTERISTIC, false);
            isFisrtOpen = savedInstanceState.getBoolean(SCREEN_SLIDE_ACTIVITY_INTENT_PARAM_ISFIRSTOPEN, false);
        } else {
            flag = getIntent().getBooleanExtra(SCREEN_SLIDE_ACTIVITY_INTENT_PARAM_CG_CHARACTERISTIC, false);
            isFisrtOpen = getIntent().getBooleanExtra(SCREEN_SLIDE_ACTIVITY_INTENT_PARAM_ISFIRSTOPEN, false);
        }
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                LogUtils.i(TAG, "PageScrolled i:" + i + " v:" + v + " i2:" + i2);
                int visibility = mBack.getVisibility();
                if (i == mPagerAdapter.getCount() - 1 && visibility == View.VISIBLE) {
                    mBack.setVisibility(View.GONE);
                } else if (i != mPagerAdapter.getCount() - 1 && visibility == View.GONE) {
                    mBack.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageSelected(int i) {
                LogUtils.w(TAG, "PageSelected" + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                LogUtils.i(TAG, "ScrollStateChanged" + i);
            }
        });

    }


    @OnClick(R.id.toolbar_back_icon)
    public void mBackClick() {
        if (isFisrtOpen) {
            ChangeLogHelper.saveAppVersion(ScreenSlideActivity.this);
            openChoiceActivity();
        } else {
            finish();
            overridePendingTransition(R.anim.translate_enter_from_right, R.anim.translate_exit_to_left);
        }
    }

    int[] images = new int[]{
            R.drawable.icon_guid_first,
            R.drawable.icon_guid_second,
            R.drawable.icon_guid_third,
    };
    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter implements View.OnClickListener {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position, this, images);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public void onClick(View v) {
            //用来点击跳转
            if (flag) {
                finish();

                overridePendingTransition(R.anim.translate_enter_from_right, R.anim.translate_exit_to_left);
            } else {
                ChangeLogHelper.saveAppVersion(ScreenSlideActivity.this);
                openChoiceActivity();
            }
        }
    }

    void openChoiceActivity() {
        Intent toChoice = new Intent(ScreenSlideActivity.this, ChoiceEnterActivity.class);
        ScreenSlideActivity.this.startActivity(toChoice);
        ScreenSlideActivity.this.finish();
        //前面一个动画，是用来定义前面一个acitivity  ,后面的一个用来定义后面的activity
        overridePendingTransition(R.anim.translate_enter_from_right, R.anim.translate_exit_to_left);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
