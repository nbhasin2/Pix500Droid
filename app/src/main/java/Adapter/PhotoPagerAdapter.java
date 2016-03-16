package Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.model.json.PhotoData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import epicara.pix500.DetailActivity;
import epicara.pix500.R;

/**
 * Created by nishant on 16-03-16.
 */
public class PhotoPagerAdapter extends PagerAdapter  {


    private ArrayList<PhotoData> imageData;
    private DetailActivity activity;

    public PhotoPagerAdapter(DetailActivity activity, ArrayList<PhotoData> imgData)
    {
        this.imageData = imgData;
        this.activity = activity;
    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param mPagerData
     */
    public void setPagerData(ArrayList<PhotoData> mPagerData) {
        imageData = mPagerData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = this.activity.getApplicationContext();

        if (position == (imageData.size() - 1)) {
            ((DetailActivity) activity).serverConnectionHelper.fetchNextPhotoPage();
        }

        ImageView imageView = new ImageView(context);
        int padding = context.getResources().getDimensionPixelSize(
                R.dimen.padding_medium);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Picasso.with(context).load(imageData.get(position).highResolutionUrl).into(imageView);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}

