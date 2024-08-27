package de.dynamicflash.adaptor;

import static de.dynamicflash.helper.DynamicFlashAppGlideModule.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.helper.DynamicFlashAppGlideModule.IMAGE_BASE_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.dynamicflash.databinding.ImageWithDescriptionBinding;
import de.dynamicflash.model.Photo;

public class PhotoFullscreenSwipeAdapter extends RecyclerView.Adapter<PhotoFullscreenSwipeAdapter.ViewHolder> {

    private final List<Photo> photos;
    private final String folder;

    // constructor
    public PhotoFullscreenSwipeAdapter(List<Photo> photos,
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

        if (photo.getComment().isEmpty()) {
            holder.binding.label.setVisibility(View.GONE);
        } else {
            holder.binding.label.setText(photo.getComment());
        }

        holder.binding.description.setVisibility(View.GONE);

        final String uri = String.format("%s/%s/%s?w=1920&h=1280&fit=inside%s", IMAGE_BASE_URL, folder, photo.getFilename(), EXTRA_IMAGE_URL_PARAMS);

        Glide.with(holder.binding.getRoot())
                .load(uri)
                .into(holder.binding.image);
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
    }
}