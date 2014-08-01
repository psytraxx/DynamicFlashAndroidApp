package de.dynamicflash.adaptor;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.dynamicflash.R;
import de.dynamicflash.model.Page;

/**
 * Created with IntelliJ IDEA.
 * User: eric
 * Date: 10/11/13
 * Time: 11:08
 */
public class GalleryListAdapter extends ArrayAdapter<Page> {

    public GalleryListAdapter(Context context, int resource,List<Page> list) {
        super(context, resource,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if (row == null) {

            LayoutInflater vi = LayoutInflater.from(getContext());
            row = vi.inflate(R.layout.gallery_list_item, null);

            holder = new ViewHolder();
            holder.title = (TextView) row.findViewById(R.id.titleID);
            holder.text = (TextView) row.findViewById(R.id.textID);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        Page page = getItem(position);

        assert page != null;
        holder.title.setText(page.getTitle());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.text.setText(Html.fromHtml(page.getBody(),Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.text.setText(Html.fromHtml(page.getBody()));
        }

        return row;
    }

    private static class ViewHolder {

        TextView title;
        TextView text;
    }
}
