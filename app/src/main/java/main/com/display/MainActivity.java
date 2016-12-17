package main.com.display;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;

import main.com.display.adapters.GridElementAdapter;
import main.com.display.models.Covers;

/**
 * Created by Muazzam on 1/2/2016.
 */
/*
 * MainActivity class that loads MainFragment
 */
public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private HorizontalGridView main_grd_uploader;
    private ArrayList<Covers> covers;
    private Button main_btn_preview;
    private GridElementAdapter adapter;
    private int IMAGE_ID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        covers = new ArrayList<Covers>();

        fillDummyData();

        main_grd_uploader = (HorizontalGridView) findViewById(R.id.main_grd_uploader);
        adapter = new GridElementAdapter(getApplicationContext(),this,covers);

        main_grd_uploader.setAdapter(adapter);

        main_btn_preview = (Button)findViewById(R.id.main_btn_preview);
        main_btn_preview.setOnClickListener(clickListener);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == main_btn_preview){

                Parcelable coversParcel = Parcels.wrap(MainActivity.this.covers);
                Bundle bundle = new Bundle();
                bundle.putParcelable("covers",coversParcel);

                Intent i = new Intent(getApplicationContext(), PreviewActivity.class);
                i.putExtras(bundle);

                startActivity(i);
            }
        }
    };
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            // requestCode will have the imageID we where we selected the new one.

            // When an Image is picked
            if (resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
//                ImageView imgView = (ImageView) findViewById(R.id.imgView);
//                // Set the Image in ImageView after decoding the String
//                imgView.setImageBitmap(BitmapFactory
//                        .decodeFile(imgDecodableString));


                Bitmap b = BitmapFactory.decodeFile(imgDecodableString);
                this.covers.add(new Covers(Bitmap.createScaledBitmap(b,120,120,false),IMAGE_ID));
                this.IMAGE_ID++;


                adapter.NewImageSelected(b, requestCode);
                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void fillDummyData(){
        this.covers.add(new Covers(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.preview),1));
        this.covers.add(new Covers(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.preview),2));
        this.covers.add(new Covers(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.preview),3));
        this.covers.add(new Covers(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.preview),4));
        this.covers.add(new Covers(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.preview),5));
    }
}
