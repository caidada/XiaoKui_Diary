package weikun.mydiary.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;
import weikun.mydiary.Adapter.FragmentAdapter;
import weikun.mydiary.R;

/**
 * Created by Weikun on 2017/10/18.
 */

public class MessageFragment extends Fragment {


    @Bind(R.id.msg_tablayout)
    TabLayout msgTablayout;
    @Bind(R.id.msg_container)
    ViewPager msgContainer;

    public MessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        initpages();
        return view;
    }

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }


    private void initpages() {
        List<String> titles = new ArrayList<>();
        titles.add("评论");
        titles.add("系统");
        for (int i = 0; i < 2; i++) {
            msgTablayout.addTab(msgTablayout.newTab().setText(titles.get(i)));
        }

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Msg_Comment());
        fragments.add(new Msg_System());

        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments, titles);

        //给ViewPager设置适配器
        msgContainer.setAdapter(mFragmentAdapteradapter);
        //将TabLayout和ViewPager关联起来。
        msgTablayout.setupWithViewPager(msgContainer);

        msgTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            int position;

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                position = tab.getPosition();

            }
        });//TabLayout事件
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
