package nyc.c4q.yuliyakaleda.asyncandthreads;

/**
 * Created by Yuliya Kaleda on 6/9/15.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<String> {

        public ImageAdapter(Context context, List<String> imageList) {
            super(context, 0, imageList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String str = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_list, parent, false);
            }
            ImageView image = (ImageView) convertView.findViewById(R.id.img);
            Picasso.with(getContext()).load(str).into(image);
            return convertView;
        }
    }