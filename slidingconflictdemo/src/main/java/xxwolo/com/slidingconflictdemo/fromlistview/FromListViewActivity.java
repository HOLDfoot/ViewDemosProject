package xxwolo.com.slidingconflictdemo.fromlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import xxwolo.com.slidingconflictdemo.R;

public class FromListViewActivity extends AppCompatActivity {

    private HorizontalListView hlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_listview);

        hlv = (HorizontalListView) findViewById(R.id.hlv);
        hlv.setAdapter(new HorizontalListAdapter(this));
    }
}
