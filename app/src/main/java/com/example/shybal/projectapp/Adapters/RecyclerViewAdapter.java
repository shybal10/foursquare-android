package com.example.shybal.projectapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shybal.projectapp.R;
import com.example.shybal.projectapp.model.DataHolder;
import com.example.shybal.projectapp.model.Venue;
import com.example.shybal.projectapp.model.VenueList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.shybal.projectapp.model.DataHolder.favVenueList;
import static com.example.shybal.projectapp.model.DataHolder.isLoggedIn;

/**
 * Created by shybal on 15/5/17.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {
    final private ListItemClickListener mOnClickListener;
    private int mNumberItems;
    public static ArrayList<Venue> venues;
    Context context;
    private ArrayList<Integer> favList;
//    public ArrayList<Venue> favVenueList;





    public RecyclerViewAdapter(int numberItems, ListItemClickListener listener, Context parent) {
        this.context = parent;
        mNumberItems = numberItems;
        mOnClickListener = listener;
        venues = new ArrayList<>();
        favList = new ArrayList<>();
      //  favVenueList = new ArrayList<>();

    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public RecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.restaurant_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean immediatelyAttachToParent = false;
        View view = inflater.inflate(layoutIdForListItem, parent, immediatelyAttachToParent);
        ItemViewHolder Holder = new ItemViewHolder(view);
        return Holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ItemViewHolder holder, final int position) {
        holder.nameTextView.setText(venues.get(position).getName());
        holder.addressTextView.setText(venues.get(position).getAddress());
        holder.ratingTextView.setText(venues.get(position).getRating());

        float distance = venues.get(position).getDistance();
        if(distance < 1) {
            holder.distanceTextView.setText("" + String.format("%.0f", distance * 1000) + "m");
        } else {
            holder.distanceTextView.setText("" + String.format("%.2f", venues.get(position).getDistance()) + "km");
        }

        holder.ratingTextView.setBackgroundColor(Color.parseColor(venues.get(position).getRatingColor()));
        holder.ratingTextView.setBackgroundResource(R.drawable.rating_border);
        GradientDrawable drawable = (GradientDrawable) holder.ratingTextView.getBackground();
        drawable.setColor(Color.parseColor(venues.get(position).getRatingColor()));
        holder.detailsTextView.setText(venues.get(position).getBrief());

        if(!isLoggedIn) {
            holder.favButton.setVisibility(View.INVISIBLE);
        }

        Picasso.with(context)
                .load(venues.get(position).getIconImageUrl()).resize(113, 113)
                .into(holder.image);

        if(DataHolder.favVenueList != null) {
            for (int i = 0; i < DataHolder.favVenueList.venues.size(); i++) {
                if (venues.get(position).getVenueId().equals(favVenueList.venues.get(i).getVenueId()) && favVenueList.venues.get(i).isFavourite()) {
                    venues.get(position).setFavourite(true);
                    favVenueList.venues.remove(i);
                }
            }
        } else {
            DataHolder.favVenueList = new VenueList();
        }

        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(venues.get(position).isFavourite()) {
                    venues.get(position).setFavourite(false);
                } else {
                    venues.get(position).setFavourite(true);
                }
                notifyDataSetChanged();
            }
        });

        if(venues.get(position).isFavourite()){
            holder.favButton.setBackgroundResource(R.drawable.favourite_icon_selected);
                favVenueList.venues.add(venues.get(position));
                DataHolder.saveFavouritesToDatabase(context);
            } else {
            holder.favButton.setBackgroundResource(R.drawable.favourite_icon);
            if(favVenueList != null) {
                favVenueList.venues.remove(venues.get(position));
                DataHolder.saveFavouritesToDatabase(context);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (venues == null) {
            return 0;
        } else {
            return venues.size();
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView ratingTextView;
        TextView addressTextView;
        TextView detailsTextView;
        TextView distanceTextView;
        ImageView image;
        Button favButton;


        public ItemViewHolder(View itemView) {
            super(itemView);
            detailsTextView = (TextView) itemView.findViewById(R.id.deatils_view);
            addressTextView = (TextView) itemView.findViewById(R.id.adress_view);
            ratingTextView = (TextView) itemView.findViewById(R.id.rating_view);
            nameTextView = (TextView) itemView.findViewById(R.id.name_view);
            image = (ImageView) itemView.findViewById(R.id.image_view);
            favButton = (Button) itemView.findViewById(R.id.fav_button);
            distanceTextView = (TextView) itemView.findViewById(R.id.distance_text_view);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    public void setData(ArrayList<Venue> venues) {
        this.venues = venues;
        notifyDataSetChanged();
    }
}