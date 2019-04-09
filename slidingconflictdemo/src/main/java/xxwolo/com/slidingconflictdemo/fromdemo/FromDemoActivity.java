package xxwolo.com.slidingconflictdemo.fromdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import xxwolo.com.slidingconflictdemo.R;

public class FromDemoActivity extends Activity {
    
    private MyViewGroup mv;
    ListView lv;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_demo);
        mv = (MyViewGroup) findViewById(R.id.myviewgroup);
        List<String> mlist = new ArrayList<String>();
        for (int i = 0; i < 40; i++) {
            mlist.add(i + "");
        }
        lv = new ListView(this);
        lv.setAdapter(new LvAdapter(this, mlist));
        
        
        int pic[] = new int[]{R.mipmap.a1, R.mipmap.a2, R.mipmap.a3, R.mipmap.a4, R.mipmap.a5, R.mipmap.a6};
        for (int i = 0; i < pic.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(pic[i]);
            
            mv.addView(imageView);
        }
        mv.addView(lv);
        test();
    }
    
    private void test() {
        int TouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        Log.d("zmr", "TouchSlop= " + TouchSlop);
    }
    
}
