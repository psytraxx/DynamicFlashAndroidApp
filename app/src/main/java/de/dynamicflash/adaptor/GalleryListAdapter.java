package de.dynamicflash.adaptor;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import de.dynamicflash.R;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:08
 */
public class GalleryListAdapter extends ArrayAdapter<Page> {

    public GalleryListAdapter(Context context, Page[] list) {
        super(context, R.layout.gallery_list, list);
    }

    // Add this method to append new items
    public void addItems(Page[] newItems) {
        addAll(newItems);
        notifyDataSetChanged();  // Notify the adapter to refresh the list
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = View.inflate(getContext(), R.layout.gallery_list_item, null);

            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.titleID);
            holder.text = convertView.findViewById(R.id.textID);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Page page = getItem(position);

        assert page != null;
        holder.title.setText(page.getTitle());
        if (page.getDescription() != null) {
            holder.text.setText(Html.fromHtml(page.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        }

        return convertView;
    }

    private static class ViewHolder {

        TextView title;
        TextView text;
    }
}
