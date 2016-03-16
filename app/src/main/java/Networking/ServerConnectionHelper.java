package Networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.Gson;
import com.model.json.PhotoData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Shared.SharedConstants;
import epicara.pix500.MainActivity;

/**
 * Created by nishant on 16-03-10.
 */
public class ServerConnectionHelper {
    String JsonURL = SharedConstants.fetchServerPhotos;
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;

    // Pages
    int currentPage = 0;
    int totalPages = 0;

    Context context;
    MainActivity activity;
    // Array of PhotoData Objects
    public ArrayList<PhotoData> photoDataList;

    public ServerConnectionHelper(MainActivity activity)
    {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.photoDataList = new ArrayList<PhotoData>();
    }

    public void fetchNextPhotoPage()
    {
        if(currentPage != 0)
        {
            fetchPhotos(currentPage+1);
        }
    }

    public void fetchFirstPhotoPage()
    {
        this.fetchPhotos(0);
    }

    public void fetchPhotos(int page)
    {

        String specificPage = SharedConstants.specificPageParam + page;
        if (page < 2)
        {
            specificPage = "";
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String url = JsonURL + specificPage;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub

                if (response != null) {

                    try {

                        currentPage = Integer.parseInt(response.getString("current_page"));
                        totalPages = Integer.parseInt(response.getString("total_pages"));

                        JSONArray photoArray = response.getJSONArray("photos");

                            for (int i = 0; i < photoArray.length(); i++)
                            {
                                JSONArray imagesArray = photoArray.getJSONObject(i).getJSONArray("images");

                                String thumbnailImage = "";
                                String highResImage = "";

                                for (int j = 0; j < imagesArray.length(); j++)
                                {
                                    String size = imagesArray.getJSONObject(j).getString("size");
                                    String imageUrl =  imagesArray.getJSONObject(j).getString("https_url");
                                    if(size.equals("440"))
                                    {
                                        thumbnailImage = imageUrl;
                                    }
                                    else if(size.equals("2048"))
                                    {
                                        highResImage = imageUrl;
                                    }
                                }

                                photoDataList.add(new PhotoData(thumbnailImage,highResImage));
                            }

                            Log.d("Size -- ","" + photoDataList.size());

                            // Update gridview datalist

                            activity.gridAdapter.setGridData(photoDataList);

                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                // TODO Auto-generated method stub

            }
        });
        requestQueue.add(request);

    }
}
