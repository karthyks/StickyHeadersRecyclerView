package com.github.karthyks.recyclerviewexample;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();
  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager mLayoutManager;
  private RecyclerViewPositionHelper mRecyclerViewHelper;
  private TextView textStaticHeader;
  private int headerPosition;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textStaticHeader = (TextView) findViewById(R.id.txt_static_header);
    recyclerView = (RecyclerView) findViewById(R.id.trip_list);
    setRecyclerView();
  }

  private void setRecyclerView() {
    recyclerView.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setAdapter(new TripListAdapter(TripListAdapter.getListHeaders()));
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
        @Override public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX,
                                             int oldScrollY) {
          manageScroll(scrollY, oldScrollY);
        }
      });
    } else {
      recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
          super.onScrollStateChanged(recyclerView, newState);
        }

        @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);
          manageScroll(dy, 0);
        }
      });
    }
  }

  private void manageScroll(int y, int oldY) {
    int firstVisibleItem, firstCompletelyVisibleItem;
    mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
    firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
    firstCompletelyVisibleItem = mRecyclerViewHelper.findFirstCompletelyVisibleItemPosition();
    if (y > oldY) {
      if (mRecyclerViewHelper.getItemStartPosition(firstCompletelyVisibleItem)
          <= headerPosition) {
        textStaticHeader.setText(mRecyclerViewHelper.getHeaderText(
            firstCompletelyVisibleItem));
        mRecyclerViewHelper.makeInvisible(firstCompletelyVisibleItem);
      } else {
        textStaticHeader.setText(mRecyclerViewHelper.getHeaderText(firstVisibleItem));
        mRecyclerViewHelper.makeInvisible(firstVisibleItem);
      }
    } else {
      if (mRecyclerViewHelper.getItemEndPosition(firstVisibleItem) >= headerPosition) {
        textStaticHeader.setText(mRecyclerViewHelper.getHeaderText(firstVisibleItem));
        mRecyclerViewHelper.makeInvisible(firstVisibleItem);
      }
    }
  }

  private void getHeaderPosition() {
    int[] pos = new int[2];
    textStaticHeader.getLocationOnScreen(pos);
    pos[1] += textStaticHeader.getHeight();
    headerPosition = pos[1];
    Log.d(TAG, "getHeaderPosition: " + headerPosition);
  }

  @Override public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    getHeaderPosition();
  }
}
