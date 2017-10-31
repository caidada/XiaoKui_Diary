package weikun.mydiary.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.joaquimley.faboptions.FabOptions;

import butterknife.Bind;
import butterknife.ButterKnife;
import weikun.mydiary.R;

public class SocialDiary extends AppCompatActivity {

    @Bind(R.id.fab_options)
    FabOptions fabOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_diary);
        ButterKnife.bind(this);
        fabOptions.setButtonsMenu(R.menu.fab_menu);
    }
}
