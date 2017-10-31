package weikun.mydiary.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import weikun.mydiary.Activity.SocialDiary;
import weikun.mydiary.Adapter.SocailAdapter;
import weikun.mydiary.Animation.CustomAnimation;
import weikun.mydiary.R;

/**
 * Created by Weikun on 2017/9/30.
 */

public class SocialFragment extends Fragment {

    @Bind(R.id.social_toolbar)
    Toolbar socialToolbar;
    @Bind(R.id.recycler_social)
    RecyclerView mRecyclerView;
    @Bind(R.id.social_refresh)
    SwipeRefreshLayout mSwipeRefreshLayou;
    private SocailAdapter mAdapter;
    private int mFirstPageItemCount = 3;
    private Handler handler = new Handler();
    protected Activity mActivity;



    public SocialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        ButterKnife.bind(this, view);
        socialToolbar.setTitle("");
        if (socialToolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(socialToolbar);
        }
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initViews();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(getContext(), SocialDiary.class));
            }
        });
        return view;
    }

    public static SocialFragment newInstance() {
        SocialFragment fragment = new SocialFragment();
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void initViews() {
        mAdapter = new SocailAdapter();
        mAdapter.openLoadAnimation(new CustomAnimation());
        mAdapter.setNotDoAnimationCount(mFirstPageItemCount);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        /*
        //列表加载动画模式
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);*/
        mSwipeRefreshLayou.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.removeAllViews();
                        //加载完成后改变状态
                        mRecyclerView.setAdapter(mAdapter);
                        mSwipeRefreshLayou.setRefreshing(false);
                        Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                }, 500);//延时操作
            }
        });
    }

}
