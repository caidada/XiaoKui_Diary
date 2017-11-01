package weikun.mydiary.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import weikun.mydiary.R;

public class DiaryActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    //左上返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.fab)
    public void onViewClicked(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        getImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 0) {
            ContentResolver resolver = getContentResolver();
            // 获得图片的uri
            Uri originalUri = data.getData();
            bitmap = null;
            try {
                Bitmap originalBitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
               // bitmap = ImageUtils.resizeImage(originalBitmap, 600);
                // 将原始图片的bitmap转换为文件
                // 上传该文件并获取url
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //insertPic(bitmap, 0);
                    }
                }).start();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * 图文详情页面选择图片
     */
    public void getImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }
    /**
     * 插入图片
     */
    /*private void insertPic(Bitmap bm, final int index) {
        AjaxParams params = new AjaxParams();
        try {
            params.put("image", LeoUtils.saveBitmap(bm));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FinalHttp fh = new FinalHttp();
        System.out.println("params=" + params.toString());
        fh.post(HttpUrlConstant.UPLOAD_PIC, params, new AjaxCallBack<Object>() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                ToastUtil.show(getApplicationContext(), "图片上传失败，请检查网络")；
            }

            @Override
            public void onSuccess(Object t) {
                super.onSuccess(t);
                System.out.println(t.toString());
                try {
                    JSONObject jsonObject = new JSONObject(t.toString());
                    String url = jsonObject.getString("recordName");
                    switch (index) {

                        case 0:
                            // 根据Bitmap对象创建ImageSpan对象
                            ImageSpan imageSpan = new ImageSpan(CreateMeetingActivity.this, bitmap);
                            // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
                            String tempUrl = "<img src=\"" + url + "\" />";
                            SpannableString spannableString = new SpannableString(tempUrl);
                            // 用ImageSpan对象替换你指定的字符串
                            spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            // 将选择的图片追加到EditText中光标所在位置
                            int index = et_detail.getSelectionStart(); // 获取光标所在位置
                            Editable edit_text = et_detail.getEditableText();
                            if (index < 0 || index >= edit_text.length()) {
                                edit_text.append(spannableString);
                            } else {
                                edit_text.insert(index, spannableString);
                            }
                            System.out.println("插入的图片：" + spannableString.toString());

                            break;

                        case 1:
                            // 与本案例无关的代码
                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }*/
}
