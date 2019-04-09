package xxwolo.com.slidingconflictdemo.fromdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xxwolo.com.slidingconflictdemo.R;

/**
 * Created by cuizehui on 2016/3/13.
 */
public class LvAdapter extends BaseAdapter {
    
    private List mlist;
    private Context mcontext;
    TextView textveiw;
    
    public LvAdapter(Context context, List list) {
        this.mlist = list;
        this.mcontext = context;
        
    }
    
    @Override
    public int getCount() {
        return mlist.size();
    }
    
    @Override
    public Object getItem(int position) {
        return null;
    }
    
    @Override
    public long getItemId(int position) {
        return 0;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder holder = null;
        if (convertView == null) {
            //给自己的adapter 缓冲view装上布局
            holder = new Viewholder();
            LayoutInflater layoutinflater = LayoutInflater.from(mcontext);
            convertView = layoutinflater.inflate(R.layout.adapter_xml, null);
            holder.mtextview = (TextView) convertView.findViewById(R.id.adptv);
            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
        }
        holder.mtextview.setText(mlist.get(position).toString());
        return convertView;
    }
    
    public final class Viewholder {
        
        public TextView mtextview;
        
    }
}
