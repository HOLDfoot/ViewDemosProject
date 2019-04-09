package xxwolo.com.slidingconflictdemo.fromlistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import xxwolo.com.slidingconflictdemo.R;

/**
 * Created by Ricky on 2016/8/25.
 */
public class HorizontalListAdapter extends BaseAdapter {

    private Context context;

    public HorizontalListAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.item_module_hlistview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        
        holder.setListView(position);

        return convertView;
    }

    private static class ViewHolder {
        ListView listView;
        
        public ViewHolder(View view) {
            listView = (ListView) view.findViewById(R.id.lv_sub);
        }
        
        private void setListView(final int position) {
            listView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return 50;
                }
            
                @Override
                public Object getItem(int i) {
                    return null;
                }
            
                @Override
                public long getItemId(int i) {
                    return i;
                }
            
                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    TextView textView = new TextView(viewGroup.getContext());
                    textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setText("item" + i);
                    return textView;
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(view.getContext(), "position= " + position + " item= " + i, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
