package com.example.shybal.projectapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shybal.projectapp.R;
import com.example.shybal.projectapp.model.DataHolder;
import com.example.shybal.projectapp.model.Venue;
import com.example.shybal.projectapp.model.VenueList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shybal on 26/5/17.
 */

public class FavouritesRecyclerViewAdapter extends RecyclerView.Adapter<FavouritesRecyclerViewAdapter.ItemViewHolder> implements Filterable{

    final private ListItemClickListener mOnClickListener;

    private int mNumberItems;
    private ArrayList<Venue> venues;
    Context context;
    private ArrayList<Integer> favList;
    private ArrayList<Venue> filteredList;
    String charString = "";





    public FavouritesRecyclerViewAdapter(int numberItems, ListItemClickListener listener, Context parent) {
        this.context = parent;
        mNumberItems = numberItems;
        mOnClickListener = listener;
        venues = new ArrayList<>();
        favList = new ArrayList<>();
        filteredList = new ArrayList<>();
        DataHolder.favouritesFiltered = new VenueList();


        //  favVenueList = new ArrayList<>();

    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public FavouritesRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.favourites_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean immediatelyAttachToParent = false;
        View view = inflater.inflate(layoutIdForListItem, parent, immediatelyAttachToParent);
        ItemViewHolder Holder = new ItemViewHolder(view);
        return Holder;
    }

    @Override
    public void onBindViewHolder(FavouritesRecyclerViewAdapter.ItemViewHolder holder, final int position) {
        holder.nameTextView.setText(venues.get(position).getName());
        holder.addressTextView.setText(venues.get(position).getAddress());
        holder.ratingTextView.setText(venues.get(position).getRating());
        holder.distanceTextView.setText("" + venues.get(position).getDistance() + "km");
        holder.ratingTextView.setBackgroundColor(Color.parseColor(venues.get(position).getRatingColor()));
        holder.detailsTextView.setText(venues.get(position).getBrief());
        Log.i("venue", venues.get(0).getName());
        Picasso.with(context)
                .load(venues.get(position).getIconImageUrl()).resize(113, 113)
                .into(holder.image);

        holder.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                venues.get(position).setFavourite(false);
                for(int i = 0; i < DataHolder.favVenueList.venues.size(); i++) {
                    if(DataHolder.favVenueList.venues.get(i).getVenueId().equals(venues.get(position).getVenueId())) {
                        DataHolder.favVenueList.venues.remove(i);
                        DataHolder.saveFavouritesToDatabase(context);
                    }
                }
                getFilter().filter(charString);
            }
        });
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
        Button closeButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            detailsTextView = (TextView) itemView.findViewById(R.id.deatils_view);
            addressTextView = (TextView) itemView.findViewById(R.id.adress_view);
            ratingTextView = (TextView) itemView.findViewById(R.id.rating_view);
            nameTextView = (TextView) itemView.findViewById(R.id.name_view);
            image = (ImageView) itemView.findViewById(R.id.image_view);
            closeButton = (Button) itemView.findViewById(R.id.close_button);
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


    /*
     * Filter methods overridden
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                charString = charSequence.toString().toLowerCase();

                if(charString.isEmpty()) {
                    filteredList = DataHolder.favVenueList.getVenues();
                } else {
                    filteredList = new ArrayList<>();
                    for(Venue venue : DataHolder.favVenueList.getVenues()) {
                        if(venue.getName().toLowerCase().contains(charString)) {
                            filteredList.add(venue);
                        }
                    }
                }
                venues = filteredList;
                DataHolder.favouritesFiltered.venues = filteredList;
                FilterResults filterResults = new FilterResults();
                filterResults.values = venues;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                venues = (ArrayList<Venue>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
