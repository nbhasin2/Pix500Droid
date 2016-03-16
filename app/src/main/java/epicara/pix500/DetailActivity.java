package epicara.pix500;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.model.json.PhotoData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Adapter.PhotoPagerAdapter;
import General.DataWrapper;
import Networking.ServerConnectionHelper;

/**
 * Created by nishant on 16-03-16.
 * Inspired by - http://www.geeks.gallery/how-to-display-image-gallery-using-viewpager-in-android/
 */
public class DetailActivity extends Activity {
    public ArrayList<PhotoData> imageData = new ArrayList<PhotoData>();
    public Bundle bundle;
    public PhotoPagerAdapter adapter;
    public ServerConnectionHelper serverConnectionHelper;
    public int currentPage = 0;
    public int totalPages = 0;
    private ViewPager viewPager;
    final TypedArray imageArrayIcon = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageviewer);

        // Initialization

        this.setupViewpager();

    }

    public void setupViewpager()
    {
        DataWrapper dw = (DataWrapper) getIntent().getSerializableExtra("data");
        this.imageData = dw.photoList();
        this.currentPage  = getIntent().getExtras().getInt("currentPage");
        this.totalPages = getIntent().getExtras().getInt("totalPages");
        this.serverConnectionHelper = new ServerConnectionHelper(this);
        this.serverConnectionHelper.currentPage = this.currentPage;
        this.serverConnectionHelper.totalPages = this.totalPages;
        int position = getIntent().getExtras().getInt("position");

        // Viewpager setup
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        PhotoPagerAdapter adapter = new PhotoPagerAdapter(this, imageData);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}