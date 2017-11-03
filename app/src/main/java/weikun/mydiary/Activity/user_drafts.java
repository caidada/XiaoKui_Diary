package weikun.mydiary.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import weikun.mydiary.Adapter.DraftAdapter;
import weikun.mydiary.R;

public class user_drafts extends AppCompatActivity {

    @Bind(R.id.draft_back)
    ImageView draftBack;
    private RecyclerView draft_list;
    private DraftAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_drafts);
        draft_list = (RecyclerView) findViewById(R.id.draft__list);
        draft_list.setLayoutManager(new LinearLayoutManager(draft_list.getContext()));
        mAdapter=new DraftAdapter(this);
        draft_list.setAdapter(mAdapter);
    }

    @OnClick(R.id.draft_back)
    public void onViewClicked() {
        finish();
    }
}
