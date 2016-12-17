package main.com.display.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import main.com.display.R;
import main.com.display.models.Covers;

/**
 * Created by Muazzam on 1/2/2016.
 */
public class GridElementAdapter extends RecyclerView.Adapter<GridElementAdapter.SimpleViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<Covers> covers;

    public GridElementAdapter(Context c,Activity ac,ArrayList<Covers> cover){
        this.context = c;
        this.activity = ac;
        this.covers = cover;
    }

    public void NewImageSelected(Bitmap bmp,int imageID){
        for(int i=0;i<covers.size();i++){
            if(covers.get(i).getId() == imageID){
                covers.get(i).setCover(bmp);
            }
        }
    }

    @Override
    public GridElementAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.upload_grid, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GridElementAdapter.SimpleViewHolder holder, final int position) {

        holder.preview.setImageBitmap(this.covers.get(position).getCover());
        holder.preview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                activity.startActivityForResult(photoPickerIntent, covers.get(position).getId());
//                  getCameraImages(context);
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                activity.startActivityForResult(galleryIntent, covers.get(position).getId());
            }
        });
    }

    public ArrayList<String> getCameraImages(Context context) {

        // Set up an array of the Thumbnail Image ID column we want
        String[] projection = {MediaStore.Images.Media.DATA};


        final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);

        ArrayList<String> result = new ArrayList<String>(cursor.getCount());

        Log.i("cursor.getCount()) :", cursor.getCount() + "");

        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            do {
                final String data = cursor.getString(dataColumn);
                Log.i("data :", data);
                result.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return result;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return this.covers.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView preview;

        public SimpleViewHolder(View view) {
            super(view);
            preview = (ImageView) view.findViewById(R.id.upgrd_img_preview);
        }
    }
}
