package com.example.shybal.projectapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shybal.projectapp.R;
import com.example.shybal.projectapp.model.Venue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shybal on 22/5/17.
 */

public class ReviewRecycleViewAdapter extends RecyclerView.Adapter<ReviewRecycleViewAdapter.ItemViewHolder> {
    final private ReviewListItemClickListener mOnClickListener;
    private int mNumberItems;
    private Context context;
    private Venue currentVenue;



    private ArrayList<Integer> favList;


    public ReviewRecycleViewAdapter(int numberItems, ReviewListItemClickListener listener, Context parent) {
        this.context = parent;
        mNumberItems = numberItems;
        mOnClickListener = listener;
        favList = new ArrayList<>();
        currentVenue = new Venue();


    }

    public interface ReviewListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public ReviewRecycleViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean immediatelyAttachToParent = false;
        View view = inflater.inflate(layoutIdForListItem, parent, immediatelyAttachToParent);
        ItemViewHolder Holder = new ItemViewHolder(view);
        return Holder;
    }

    @Override
    public void onBindViewHolder(ReviewRecycleViewAdapter.ItemViewHolder holder, final int position) {

        holder.userNameTextView.setText(currentVenue.getReviews().get(position).getUser().getFullName());
        holder.reviewTextView.setText(currentVenue.getReviews().get(position).getReviewText());
        holder.dateTextView.setText(currentVenue.getReviews().get(position).getFormattedDate());
        Picasso.with(context)
                .load(currentVenue.getReviews().get(position).getUser().getImageUrl()).resize(113, 113)
                .into(holder.userImageView);



    }

    @Override
    public int getItemCount() {
        int size = currentVenue.getReviews().size();
        if (currentVenue.getReviews() == null) {
            return 0;
        } else {
            return size;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView userNameTextView;
        private TextView dateTextView;
        private TextView reviewTextView;
        private CircleImageView userImageView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            userNameTextView = (TextView) itemView.findViewById(R.id.name_id);
            dateTextView = (TextView) itemView.findViewById(R.id.date_id);
            reviewTextView = (TextView) itemView.findViewById(R.id.review_id);
            userImageView = (CircleImageView) itemView.findViewById(R.id.profile_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

    }

    public void setData(Venue venue) {
        currentVenue = venue;
        notifyDataSetChanged();
    }

}
