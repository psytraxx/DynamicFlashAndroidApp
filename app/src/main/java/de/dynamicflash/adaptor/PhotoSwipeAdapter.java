package de.dynamicflash.adaptor;

import static de.dynamicflash.GalleryApplication.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.GalleryApplication.IMAGE_BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import coil.Coil;
import coil.request.ImageRequest;
import de.dynamicflash.databinding.ImageWithDescriptionBinding;
import de.dynamicflash.model.Photo;

public class PhotoSwipeAdapter extends RecyclerView.Adapter<PhotoSwipeAdapter.ViewHolder> {

    private final List<Photo> photos;
    private final String folder;

    // constructor
    public PhotoSwipeAdapter(List<Photo> photos,
                             String folder) {
        this.photos = photos;
        this.folder = folder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ImageWithDescriptionBinding binding = ImageWithDescriptionBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.bind(photo, folder);
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageWithDescriptionBinding binding;

        public ViewHolder(ImageWithDescriptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Photo photo, String folder) {
            if (photo.getComment().isEmpty()) {
                binding.label.setVisibility(View.GONE);
            } else {
                binding.label.setText(photo.getComment());
            }

            binding.description.setVisibility(View.GONE);

            final String uri = String.format("%s/%s/%s?w=1920&h=1280&fit=inside%s", IMAGE_BASE_URL, folder, photo.getFilename(), EXTRA_IMAGE_URL_PARAMS);

            Context context = itemView.getContext();
            ImageRequest request = new ImageRequest.Builder(context)
                    .data(uri)
                    .target(binding.image)
                    .build();

            Coil.imageLoader(context).enqueue(request);
        }
    }
}