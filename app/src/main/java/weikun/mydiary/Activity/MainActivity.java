package weikun.mydiary.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import weikun.mydiary.BackHandlerHelper;
import weikun.mydiary.BottomNavigationViewHelper;
import weikun.mydiary.Fragment.DiaryFragment;
import weikun.mydiary.Fragment.MessageFragment;
import weikun.mydiary.Fragment.SocialFragment;
import weikun.mydiary.Fragment.UserFragment;
import weikun.mydiary.R;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.navigation)
    BottomNavigationView navigation;
    private Fragment currentFragment = new Fragment();
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int currentIndex = 0;
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_socail:
                    //fragment = new SocialFragment();
                    currentIndex = 0;
                    break;
                case R.id.navigation_diary:
                    //fragment = new DiaryFragment();
                    currentIndex = 1;
                    break;
                case R.id.navigation_message:
                    //fragment = new MessageFragment();
                    currentIndex = 2;
                    break;
                case R.id.navigation_user:
                    //fragment = new UserFragment();
                    currentIndex = 3;
                    break;
            }
            showFragment();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) { // “内存重启”时调用

            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT, 0);

            //注意，添加顺序要跟下面添加的顺序一样！！！！
            mFragments.removeAll(mFragments);
            mFragments.add(fragmentManager.findFragmentByTag(0 + ""));
            mFragments.add(fragmentManager.findFragmentByTag(1 + ""));
            mFragments.add(fragmentManager.findFragmentByTag(2 + ""));
            mFragments.add(fragmentManager.findFragmentByTag(3 + ""));

            //恢复fragment页面
            restoreFragment();


        } else {      //正常启动时调用
            mFragments.add(SocialFragment.newInstance());
            mFragments.add(DiaryFragment.newInstance());
            mFragments.add(MessageFragment.newInstance());
            mFragments.add(UserFragment.newInstance());
            showFragment();
        }
        BottomNavigationViewHelper.disableShiftMode(navigation);//去除菜单项动画
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);//点击事件
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if (!mFragments.get(currentIndex).isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.main_container, mFragments.get(currentIndex), "" + currentIndex);  //第三个参数为添加当前的fragment时绑定一个tag
        } else {
            transaction
                    .hide(currentFragment)
                    .show(mFragments.get(currentIndex));
        }
        currentFragment = mFragments.get(currentIndex);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //“内存重启”时保存当前的fragment名字
        outState.putInt(CURRENT_FRAGMENT, currentIndex);
        super.onSaveInstanceState(outState);
    }

    /**
     * 恢复fragment
     */
    private void restoreFragment() {
        FragmentTransaction mBeginTreansaction = fragmentManager.beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {

            if (i == currentIndex) {
                mBeginTreansaction.show(mFragments.get(i));
            } else {
                mBeginTreansaction.hide(mFragments.get(i));
            }

        }
        mBeginTreansaction.commit();
        //把当前显示的fragment记录下来
        currentFragment = mFragments.get(currentIndex);
    }
}
