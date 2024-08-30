package de.dynamicflash.adaptor;

import static de.dynamicflash.GalleryApplication.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.GalleryApplication.IMAGE_BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import coil.Coil;
import coil.request.ImageRequest;
import de.dynamicflash.databinding.FragmentPhotoGridItemBinding;
import de.dynamicflash.model.Photo;

public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.ViewHolder> {
    private ItemClickListener itemClickListener;

    private List<Photo> photos = new ArrayList<>();
    private final String folder;

    public PhotoGridAdapter(String folder) {
        this.folder = folder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentPhotoGridItemBinding binding = FragmentPhotoGridItemBinding.inflate(inflater, parent, false);
        return new PhotoGridAdapter.ViewHolder(binding, folder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo item = photos.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void addAll(List<Photo> photos) {
        this.photos = photos;
        notifyItemRangeChanged(0, photos.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private final FragmentPhotoGridItemBinding binding;
        private final String folder;

        public ViewHolder(@NonNull FragmentPhotoGridItemBinding binding, String folder) {
            super(binding.getRoot());
            this.binding = binding;
            this.folder = folder;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(Photo photo) {
            final String uri = String.format("%s/%s/%s?w=320&h=240&fit=inside%s", IMAGE_BASE_URL, folder, photo.getFilename(), EXTRA_IMAGE_URL_PARAMS);

            Context context = binding.getRoot().getContext();
            ImageRequest request = new ImageRequest.Builder(context)
                    .data(uri)
                    .target(binding.image)
                    .build();

            Coil.imageLoader(context).enqueue(request);
        }


        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getBindingAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // parent activity/fragment will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}