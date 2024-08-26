package de.dynamicflash.adaptor;

import static de.dynamicflash.helper.DynamicFlashAppGlideModule.EXTRA_IMAGE_URL_PARAMS;
import static de.dynamicflash.helper.DynamicFlashAppGlideModule.IMAGE_BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.dynamicflash.databinding.ProjectItemBinding;
import de.dynamicflash.model.Photo;

public class PhotoFullscreenSwipeAdapter extends RecyclerView.Adapter<PhotoFullscreenSwipeAdapter.PhotoFullScreenViewHolder> {

    private final Context context;
    private final List<Photo> photos;
    private final String folder;

    // constructor
    public PhotoFullscreenSwipeAdapter(Context activity,
                                       List<Photo> photos,
                                       String folder) {
        this.context = activity;
        this.photos = photos;
        this.folder = folder;
    }

    @NonNull
    @Override
    public PhotoFullScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ProjectItemBinding binding = ProjectItemBinding.inflate(inflater, parent, false);
        return new PhotoFullscreenSwipeAdapter.PhotoFullScreenViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoFullScreenViewHolder holder, int position) {
        Photo photo = photos.get(position);

        if (photo.getComment().isEmpty()) {
            holder.binding.label.setVisibility(View.GONE);
        } else {
            holder.binding.label.setText(photo.getComment());
        }

        final String uri = String.format("%s/%s/%s?w=1920&h=1280&fit=inside%s", IMAGE_BASE_URL, folder, photo.getFilename(), EXTRA_IMAGE_URL_PARAMS);

        Glide.with(context)
                .load(uri)
                .into(holder.binding.image);
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public static class PhotoFullScreenViewHolder extends RecyclerView.ViewHolder {
        final ProjectItemBinding binding;

        public PhotoFullScreenViewHolder(ProjectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}