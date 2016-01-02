package main.com.display.models;

import android.graphics.Bitmap;

/**
 * Created by Muazzam on 1/2/2016.
 */
public class Covers {

    private Bitmap image;
    private int id;

    public Covers(Bitmap image,int id){

        this.image = image;
        this.id = id;
    }

    public int getId(){ return this.id; }
    public Bitmap getCover() { return this.image; }

    public void setCover(Bitmap img) { this.image = img; }
    public void setId(int id) { this.id = id; }

}
