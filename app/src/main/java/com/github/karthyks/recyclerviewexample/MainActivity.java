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
        int firstVisibleItem, visibleItemCount, totalItemCount;

        @Override public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX,
                                             int oldScrollY) {
          mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
          visibleItemCount = recyclerView.getChildCount();
          totalItemCount = mRecyclerViewHelper.getItemCount();
          firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
          Log.d(TAG, "onScrolled: first visible : " + firstVisibleItem);
          firstVisibleItem = mRecyclerViewHelper.findFirstCompletelyVisibleItemPosition();
          Log.d(TAG, "onScrolled: first completely visible : " + firstVisibleItem);
          if (scrollY > oldScrollY) {
            Log.d(TAG, "onScrolled: Up");
          } else {
            Log.d(TAG, "onScrolled: Down");
          }

        }
      });
    } else {
      recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
        int firstVisibleItem, firstCompletelyVisibleItem;

        @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
          super.onScrollStateChanged(recyclerView, newState);
        }

        @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);
          mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
          firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
          firstCompletelyVisibleItem = mRecyclerViewHelper.findFirstCompletelyVisibleItemPosition();
          if (dy > 0) {
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
      });
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
