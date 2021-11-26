package com.example.homepage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RestoAdapter extends RecyclerView.Adapter<RestoAdapter.RestaurantViewHolder> implements Filterable {

    private List<RestoModel> restaurantModels, restaurantModelsAll;
    private Context context;
//    private OnItemClickListener mListener;

//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }

//    public void setOnItemClickListener(OnItemClickListener listener) {
//        mListener = listener;
//    }

    public RestoAdapter(List<RestoModel> restaurantModels, Context context) {
        this.restaurantModels = restaurantModels;
        restaurantModelsAll = new ArrayList<>(restaurantModels);
        this.context = context;
    }

    @NonNull
    @Override
    public RestoAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resto_list, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override   
    public void onBindViewHolder(@NonNull RestoAdapter.RestaurantViewHolder holder, int position) {
        holder.tvName.setText(restaurantModels.get(position).getName());
        holder.tvCity.setText(restaurantModels.get(position).getCity());
        Glide.with(context)
                .load(restaurantModels.get(position).getPictureId())
                .into(holder.ivPicture);
        holder.cvRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", restaurantModels.get(position).getId());
            intent.putExtra("description", restaurantModels.get(position).getDescription());
            intent.putExtra("name", restaurantModels.get(position).getName());
            intent.putExtra("city", restaurantModels.get(position).getCity());
            intent.putExtra("pictureId", restaurantModels.get(position).getPictureId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return (restaurantModels != null) ? restaurantModels.size() : 0;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvCity;
        private ImageView ivPicture;
        private CardView cvRestaurant;
//        private RelativeLayout relativeLayout;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.list_namaResto_txt);
            tvCity = itemView.findViewById(R.id.list_kota_txt);
            ivPicture = itemView.findViewById(R.id.list_poster);
            cvRestaurant = itemView.findViewById(R.id.cv_resto);
            cvRestaurant.setBackgroundResource(R.drawable.transparent_background);

//            relativeLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
        }
    }

    @Override
    public Filter getFilter() {
        return restoFilter;
    }

    private Filter restoFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<RestoModel> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(restaurantModelsAll);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (RestoModel item : restaurantModelsAll) {
                    if (item.getName().toLowerCase().contains(filterPattern) || item.getCity().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            restaurantModels.clear();
            restaurantModels.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
