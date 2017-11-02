package weikun.mydiary.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import weikun.mydiary.Activity.DiaryActivity;
import weikun.mydiary.Activity.DiaryEditActivity;
import weikun.mydiary.Adapter.RecyclerViewAdapter;
import weikun.mydiary.Animation.SpacesItemDecoration;
import weikun.mydiary.BackHandlerHelper;
import weikun.mydiary.FragmentBackHandler;
import weikun.mydiary.R;
import weikun.mydiary.db.Note;
import weikun.mydiary.db.NoteDao;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Weikun on 2017/9/30.
 */

public class DiaryFragment extends Fragment implements FragmentBackHandler {

    @Bind(R.id.diary_toolbar)
    Toolbar diaryToolbar;
    @Bind(R.id.recycler_diary)
    XRecyclerView recyclerDiary;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    @Bind(R.id.toolbar_container)
    FrameLayout toolbarContainer;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    protected View mRootView;
    private RecyclerViewAdapter mAdapter;
    private List<Note> DiaryList;
    private NoteDao noteDao;
    private int groupId;//分类ID
    private String groupName;
    /**
     * 是否创建
     */
    protected boolean isCreate = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this, mRootView);
        Init();
        if()
        refreshDiaryList();
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
        refreshDiaryList();
        super.onActivityCreated(savedInstanceState);
    }

    protected void Init() {
        /**
         * 下拉刷新
         * */
       /* diaryRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        });*/

        noteDao = new NoteDao(getContext());
        /****************** 设置XRecyclerView属性 **************************/
        recyclerDiary.addItemDecoration(new SpacesItemDecoration(0));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//竖向列表
        recyclerDiary.setLayoutManager(layoutManager);
        recyclerDiary.setLoadingMoreEnabled(true);//开启上拉加载
        recyclerDiary.setPullRefreshEnabled(true);//开启下拉刷新
        recyclerDiary.setRefreshProgressStyle(ProgressStyle.SquareSpin);
        recyclerDiary.setLoadingMoreProgressStyle(ProgressStyle.BallScale);
        /****************** 设置XRecyclerView属性 **************************/

        mAdapter = new RecyclerViewAdapter();
        mAdapter.setmNotes(DiaryList);
        recyclerDiary.setAdapter(mAdapter);
        recyclerDiary.setLoadingListener(new MyLoadingListener());
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Note note) {
                Toast.makeText(getContext(),note.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DiaryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", note);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
        mAdapter.setOnItemLongClickListener(new RecyclerViewAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final Note note) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("确定删除日记？");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int ret = noteDao.deleteNote(note.getId());
                        if (ret > 0){
                            Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                            refreshDiaryList();
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
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

    /** 上拉加载和下拉刷新事件 **/
    private class MyLoadingListener implements XRecyclerView.LoadingListener{

        @Override
        public void onRefresh() {//下拉刷新
            recyclerDiary.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerDiary.refreshComplete();
                    refreshDiaryList();
                }
            }, 1000);
        }

        @Override
        public void onLoadMore() {//上拉加载
            recyclerDiary.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerDiary.loadMoreComplete();
                    refreshDiaryList();
                }
            }, 1000);
        }
    }

    //刷新日记列表
    private void refreshDiaryList(){
        DiaryList = noteDao.queryNotesAll(groupId);
        //Log.i(TAG, "###noteList: "+noteList);
        mAdapter.setmNotes(DiaryList);
        mAdapter.notifyDataSetChanged();
    }
    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), DiaryEditActivity.class);
        intent.putExtra("groupName", groupName);
        intent.putExtra("flag", 0);
        startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&isCreate) {
            //相当于Fragment的onResume
            refreshDiaryList();
        } else {
            //相当于Fragment的onPause
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreate = true;
    }
}
