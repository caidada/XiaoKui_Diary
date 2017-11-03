package weikun.mydiary.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import weikun.mydiary.R;

/**
 * Created by 苏木 on 2017/11/3.
 */

public class DraftAdapter extends RecyclerView.Adapter<DraftAdapter.ViewHolder> {

    private Context mContext;
    public DraftAdapter(Context context) {
        this.mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_draft, parent, false);
        return new ViewHolder(view);
    }//item加载卡片布局

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "你点击了卡片" + position, Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(v.getContext(), Course_Details.class);
                //v.getContext().startActivity(intent);
            }
        });//卡片点击事件
    }
    @Override
    public int getItemCount() {
        return 10;
    }//卡片数

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }
}
