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

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if (row == null) {

            row = View.inflate(getContext(), R.layout.gallery_list_item, null);

            holder = new ViewHolder();
            holder.title = row.findViewById(R.id.titleID);
            holder.text = row.findViewById(R.id.textID);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        Page page = getItem(position);

        assert page != null;
        holder.title.setText(page.getTitle());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.text.setText(Html.fromHtml(page.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.text.setText(Html.fromHtml(page.getDescription()));
        }

        return row;
    }

    private static class ViewHolder {

        TextView title;
        TextView text;
    }
}
