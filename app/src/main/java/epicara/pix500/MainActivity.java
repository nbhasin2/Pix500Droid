package epicara.pix500;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.model.json.PhotoData;

import java.util.ArrayList;

import Adapter.PhotoGridAdapter;
import General.DataWrapper;
import Networking.ServerConnectionHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Variables

    public ServerConnectionHelper serverconnectionhelper;

    // Grid view related variables

    private GridView gridView;
    public PhotoGridAdapter gridAdapter;
    private ArrayList<PhotoData> gridDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialization

        this.fetchPhotos();
        this.setUpGridView();
    }

    // Initializes the server connection helper and fetches data from the server

    public void fetchPhotos()
    {
        this.serverconnectionhelper = new ServerConnectionHelper(this);
        this.serverconnectionhelper.fetchFirstPhotoPage();
    }

    // Setup Gridview to display the newly fetched data from the server

    public void setUpGridView()
    {
        this.gridView = (GridView) findViewById(R.id.gridView);
        this.gridDataList = this.serverconnectionhelper.photoDataList;
        this.gridAdapter = new PhotoGridAdapter(this, R.layout.grid_item_layout, this.gridDataList);
        this.gridView.setAdapter(this.gridAdapter);
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("data", new DataWrapper(gridDataList));
                intent.putExtra("currentPage", serverconnectionhelper.currentPage);
                intent.putExtra("totalPages", serverconnectionhelper.totalPages);
                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }
}
