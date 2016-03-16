package Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.model.json.PhotoData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.*;

import java.util.ArrayList;

import epicara.pix500.MainActivity;
import epicara.pix500.R;

/**
 * Created by nishant on 16-03-16.
 */
public class PhotoGridAdapter extends ArrayAdapter<PhotoData> {

    //private final ColorMatrixColorFilter grayscaleFilter;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<PhotoData> gridData = new ArrayList<PhotoData>();

    public PhotoGridAdapter(Context mContext, int layoutResourceId, ArrayList<PhotoData> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.gridData = mGridData;
    }


    /**
     * Updates grid data and refresh grid items.
     *
     * @param mGridData
     */
    public void setGridData(ArrayList<PhotoData> mGridData) {
        this.gridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        // Check if the position is last and if so then we fetch more data.
        // This can be slow and should be improved later by fetching code while we scroll the view.

        if(position == (gridData.size() - 1))
        {
            ((MainActivity) mContext).serverconnectionhelper.fetchNextPhotoPage();
        }

        // View setup

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        PhotoData item = gridData.get(position);
        Picasso.with(mContext).load(item.thumbnailUrl).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}