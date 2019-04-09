package xxwolo.com.slidingconflictdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lv1, lv2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list1.add(i + "");
        }
        lv1.setAdapter(new MyAdapter(list1));
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list2.add("00" + i);
        }
        lv2.setAdapter(new MyAdapter(list2));
    }
    
    private class MyAdapter extends BaseAdapter {
        
        private List<String> list;
        
        public MyAdapter(List<String> data) {
            this.list = data;
        }
        
        @Override
        public int getCount() {
            return list.size();
        }
        
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MainActivity.this);
            
            textView.setText(list.get(position));
            
            return textView;
        }
    }
}
