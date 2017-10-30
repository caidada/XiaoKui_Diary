package weikun.mydiary.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import weikun.mydiary.Adapter.RecyclerViewAdapter;
import weikun.mydiary.BackHandlerHelper;
import weikun.mydiary.FragmentBackHandler;
import weikun.mydiary.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Weikun on 2017/9/30.
 */

public class DiaryFragment extends Fragment implements FragmentBackHandler {

    @Bind(R.id.diary_toolbar)
    Toolbar diaryToolbar;
    @Bind(R.id.recycler_diary)
    RecyclerView recyclerDiary;
    @Bind(R.id.diary_refresh)
    SwipeRefreshLayout diaryRefresh;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    private Handler handler = new Handler();
    protected Activity mActivity;
    protected View mRootView;


    public DiaryFragment() {
    }
    /**
     * 说明：在此处保存全局的Context
     *
     * @param context 上下文
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this, mRootView);
        Init();
        return mRootView;

    }

    public static DiaryFragment newInstance() {
        DiaryFragment fragment = new DiaryFragment();
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.diary_toolbar, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            return true;
        } else {
            return BackHandlerHelper.handleBackPress(this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * 说明：返回当前View
     *
     * @return view
     */
    protected View getContentView() {
        return mRootView;
    }

    protected void Init()
    {
        recyclerDiary.setLayoutManager(new LinearLayoutManager(recyclerDiary.getContext()));
        recyclerDiary.setAdapter(new RecyclerViewAdapter(getActivity()));
        /**
         * 下拉刷新
         * */
        diaryRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerDiary.removeAllViews();
                        //加载完成后改变状态
                        recyclerDiary.setAdapter(new RecyclerViewAdapter(getActivity()));
                        diaryRefresh.setRefreshing(false);
                        Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                }, 500);//延时操作
            }
        });
        diaryToolbar.setTitle("");
        if (diaryToolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(diaryToolbar);
        }
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView.setVoiceSearch(true);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        //searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(getActivity().findViewById(R.id.social_container), "Query: " + query, Snackbar.LENGTH_LONG)
                        .show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
        setHasOptionsMenu(true);
    }
}
