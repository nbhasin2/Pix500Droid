package General;

import com.model.json.PhotoData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nishant on 16-03-16.
 * ref - http://stackoverflow.com/questions/15747727/pass-arraylist-of-user-defined-objects-to-intent-android/15747819#
 */
public class DataWrapper implements Serializable {

    private ArrayList<PhotoData> photos;

    public DataWrapper(ArrayList<PhotoData> data) {
        this.photos = data;
    }

    public ArrayList<PhotoData> photoList() {
        return this.photos;
    }

}