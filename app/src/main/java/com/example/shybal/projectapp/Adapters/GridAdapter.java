package com.example.shybal.projectapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.shybal.projectapp.R;
import com.example.shybal.projectapp.model.Venue;
import com.squareup.picasso.Picasso;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by shybal on 22/5/17.
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private Venue currentVenue;
    private static int count = 0;


    public GridAdapter(Context c, Venue venue) {
        currentVenue = venue;
        mContext = c;
    }

    public int getCount() {
            int size = currentVenue.getPhotos().size();
            return size;

    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View imageView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            Log.i(TAG,String.valueOf(position));

            // if it's not recycled, initialize some attributes
            imageView = new View(mContext);
            imageView = inflater.inflate(R.layout.image_list, null);
            ImageView image = (ImageView) imageView.findViewById(R.id.imageView1);

            Picasso.with(mContext)
                    .load(currentVenue.getPhotos().get(position).getImageUrl()).resize(113, 113)
                    .into(image);

        }
        else {

            imageView = (View) convertView;

        }
        return imageView;
    }

    /*
    public void setData(Venue venue){
        currentVenue = venue;
        notifyDataSetChanged();
    }
    */

}
