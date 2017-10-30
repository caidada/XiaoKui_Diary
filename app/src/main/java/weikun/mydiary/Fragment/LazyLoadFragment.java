package weikun.mydiary.Fragment;

/**
 * Created by Weikun on 2017/10/23.
 */

/**
 * 懒加载Fragment
 *
 * 可以加载数据的条件：
 * 1.视图已经初始化
 * 2.视图对用户可见
 */

public abstract class LazyLoadFragment extends BaseFragment {

    public boolean hasInitialized = false;//视图是否已经初始化
    public boolean hasLoaded = false;//视图是否已经加载过数据

    /**
     * 初始化
     */
    @Override
    protected void init() {
        hasInitialized = true;
        isCanLoadData();
    }

    /**
     * 判断是否可以加载数据，如果可以便进行数据的加载
     */
    private void isCanLoadData() {
        if (!hasInitialized) {
            return;
        }

        //如果可见且未曾加载过数据
        if (getUserVisibleHint() && !hasLoaded) {
            lazyLoad();
            hasLoaded = true;
        } else if (hasLoaded)
            stopLoad();
    }


    /**
     * 当视图初始化并且对可见时加载数据
     */
    public abstract void lazyLoad();


    /**
     * 当该视图对用户不可见并且已经加载过数据的时候，如果需要在切换到其他页面时停止加载数据，通过覆写此方法实现
     */
    public void stopLoad() {
    }


    /**
     * 说明：当前视图可见性发生变化时调用该方法
     *
     * @param isVisibleToUser 当前视图是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }


    /**
     * 视图销毁时将Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hasInitialized = false;
        hasLoaded = false;
    }

}
