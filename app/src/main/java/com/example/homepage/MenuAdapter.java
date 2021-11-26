package com.example.homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ListViewHolder> {
    private List<String> dataList;

    public MenuAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MenuAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.menu_list, parent, false);
        return new MenuAdapter.ListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.tv_menu.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public static final class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_menu;

        public ListViewHolder(View itemView) {
            super(itemView);
            tv_menu = itemView.findViewById(R.id.tv_menu);
        }
    }
}
