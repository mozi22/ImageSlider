package main.com.display.ParcelConverter;

import android.os.Parcel;


import org.parceler.ParcelConverter;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import main.com.display.models.Covers;


/**
 * Created by Muazzam on 7/21/2016.
 */

public class ParcelCoversConverter implements ParcelConverter<List<Covers>> {


    @Override
    public void toParcel(List<Covers> input, Parcel parcel) {
        if (input == null) {
            parcel.writeInt(-1);
        }
        else {
            parcel.writeInt(input.size());
            for (Covers item : input) {
                parcel.writeParcelable(Parcels.wrap(item), 0);
            }
        }
    }

    @Override
    public List<Covers> fromParcel(Parcel parcel) {
        int size = parcel.readInt();
        if (size < 0) return null;
        List<Covers> items = new ArrayList<Covers>();
        for (int i = 0; i < size; ++i) {
            items.add((Covers) Parcels.unwrap(parcel.readParcelable(Covers.class.getClassLoader())));
        }
        return items;
    }
}
