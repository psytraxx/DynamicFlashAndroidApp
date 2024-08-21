package de.dynamicflash.adaptor;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import de.dynamicflash.R;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:08
 */
public class GalleryListAdapter extends ArrayAdapter<Page> {

    public GalleryListAdapter(Context context, ArrayList<Page> list) {
        super(context, R.layout.gallery_list, list);
    }

    public void addPages(ArrayList<Page> newPages) {
        addAll(newPages);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gallery_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Page page = getItem(position);

        if (page != null) {
            holder.title.setText(page.getTitle());
            holder.text.setText(page.getDescription() != null ? Html.fromHtml(page.getDescription(), Html.FROM_HTML_MODE_COMPACT) : "");
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView text;

        ViewHolder(View view) {
            title = view.findViewById(R.id.titleID);
            text = view.findViewById(R.id.textID);
        }
    }
}
