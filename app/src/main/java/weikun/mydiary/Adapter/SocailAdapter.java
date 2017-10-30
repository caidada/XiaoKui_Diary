package weikun.mydiary.Adapter;

import android.support.v7.widget.CardView;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import weikun.mydiary.Data.DataServer;
import weikun.mydiary.Data.Status;
import weikun.mydiary.R;

/**
 * Created by Weikun on 2017/10/23.
 */

public class SocailAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
    public SocailAdapter() {
        super(R.layout.diary_card, DataServer.getSampleData(10));
    }
    @Override
    protected void convert(BaseViewHolder helper, Status item) {
       /* helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetName);
        switch (helper.getLayoutPosition() %
                3) {
            case 0:
                helper.setImageResource(R.id.img, R.mipmap.animation_img1);
                break;
            case 1:
                helper.setImageResource(R.id.img, R.mipmap.animation_img2);
                break;
            case 2:
                helper.setImageResource(R.id.img, R.mipmap.animation_img3);
                break;
        }
        helper.setText(R.id.tweetName, "Hoteis in Rio de Janeiro");
        String msg = "\"He was one of Australia's most of distinguished artistes, renowned for his portraits\"";
        ((TextView) helper.getView(R.id.tweetText)).setText(SpannableStringUtils.getBuilder(msg).append("landscapes and nedes").setClickSpan(clickableSpan).create());
        ((TextView) helper.getView(R.id.tweetText)).setMovementMethod(ClickableMovementMethod.getInstance());
        ((TextView) helper.getView(R.id.tweetText)).setFocusable(false);
        ((TextView) helper.getView(R.id.tweetText)).setClickable(false);
        ((TextView) helper.getView(R.id.tweetText)).setLongClickable(false);*/
        ((TextView) helper.getView(R.id.tweetName)).setText("我是日记标题");
        ((TextView) helper.getView(R.id.tweetDate)).setText("我是日记日期");
        ((TextView) helper.getView(R.id.tweetText)).setText("我是日记内容");

    }
}
