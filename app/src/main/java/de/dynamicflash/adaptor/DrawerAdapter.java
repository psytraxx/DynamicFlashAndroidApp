package de.dynamicflash.adaptor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.dynamicflash.databinding.DrawerListItemBinding;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private final String[] items;

    public DrawerAdapter(String[] items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DrawerListItemBinding binding = DrawerListItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final DrawerListItemBinding binding;

        public ViewHolder(@NonNull DrawerListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String item) {
            binding.text1.setText(item);
        }
    }
}