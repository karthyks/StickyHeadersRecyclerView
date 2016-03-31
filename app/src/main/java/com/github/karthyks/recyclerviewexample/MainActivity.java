package com.github.karthyks.recyclerviewexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();
  private TripListBuilder tripListBuilder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    TextView textStaticHeader = (TextView) findViewById(R.id.txt_static_header);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.trip_list);
    tripListBuilder = new TripListBuilder(recyclerView, textStaticHeader).withContext(this);
  }


  @Override public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    tripListBuilder.getHeaderEndPosition();
  }
}
