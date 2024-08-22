package de.dynamicflash.adaptor;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

import de.dynamicflash.R;
import de.dynamicflash.databinding.GalleryListItemBinding;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:08
 */
public class GalleryListAdapter extends ArrayAdapter<Page> {

    private final LayoutInflater inflater;

    public GalleryListAdapter(Context context, List<Page> list) {
        super(context, R.layout.gallery_list, list);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        GalleryListItemBinding binding;

        if (convertView == null) {

            binding = GalleryListItemBinding.inflate(inflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);

        } else {
            binding = (GalleryListItemBinding) convertView.getTag();
        }

        Page page = getItem(position);

        if (page != null) {
            binding.titleID.setText(page.getTitle());
            if (page.getDescription() != null) {
                binding.textID.setText(Html.fromHtml(page.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            }
        }

        return convertView;
    }
}
