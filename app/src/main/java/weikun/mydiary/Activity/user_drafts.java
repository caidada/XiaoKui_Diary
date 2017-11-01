package weikun.mydiary.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import weikun.mydiary.R;

public class user_drafts extends AppCompatActivity {

    @Bind(R.id.draft_back)
    ImageView draftBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_drafts);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.draft_back)
    public void onViewClicked() {
        finish();
    }
}
