package com.ensibuuko.test.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ensibuuko.test.R;
import com.ensibuuko.test.ui.models.Photos;
import com.ensibuuko.test.ui.services.ClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{
    List<Photos> photos;
    private static ClickListener clickListener;
    Context context;

    public PhotoAdapter(Context context,List<Photos> photos){
        this.photos = photos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);

        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Photos photo = photos.get(position);
        holder.item_title.setText(photo.getTitle());

        Picasso.get().load(photo.getThumbnailUrl()).placeholder(R.drawable.ic_placeholder).into(holder.item_image);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView item_title;
        private final ImageView item_image;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            view.setOnClickListener(this);

            item_title = view.findViewById(R.id.item_title);
            item_image = view.findViewById(R.id.item_image);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(),view);
        }
    }

    public void setOnItemClickListener(ClickListener click) {
        clickListener = click;
    }

}
