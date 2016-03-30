package com.github.karthyks.recyclerviewexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

  private List<String> headerList;

  public TripListAdapter(List<String> headerList) {
    this.headerList = headerList;
  }

  @Override public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_item, parent,
        false);
    return new TripViewHolder(view);
  }

  @Override public void onBindViewHolder(TripViewHolder holder, int position) {
    holder.textViewHeader.setText(headerList.get(position));
  }

  @Override public int getItemCount() {
    return headerList.size();
  }

  public static class TripViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewHeader;
    public TripViewHolder(View itemView) {
      super(itemView);
      textViewHeader = (TextView) itemView.findViewById(R.id.txt_header);
    }

    public TextView getTextViewHeader() {
      return textViewHeader;
    }
  }

  public static List<String> getListHeaders() {
    List<String> strings = new LinkedList<>();
    strings.add("Header0");
    strings.add("Header1");
    strings.add("Header2");
    strings.add("Header3");
    strings.add("Header4");
    strings.add("Header5");
    strings.add("Header6");
    strings.add("Header7");
    strings.add("Header8");
    strings.add("Header9");
    return strings;
  }
}
