package tw.soleil.offerwall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.koushikdutta.ion.Ion;
import tw.soleil.offerwall.R;
import tw.soleil.core.parse.Offer;
import tw.soleil.core.parse.Wall;

import java.util.List;

/**
 * Created by edward_chiang on 15/1/1.
 */
public class WallAdapter extends ArrayAdapter<Wall> {
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public WallAdapter(Context context, int resource, List<Wall> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if (rootView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflater.inflate(R.layout.cell_wall, null);
        }
        Wall currentWall = getItem(position);

        TextView appNameTextView = (TextView)rootView.findViewById(R.id.wall_app_name_text_view);
        appNameTextView.setText(currentWall.getAppName());

        ImageView imageView = (ImageView)rootView.findViewById(R.id.wall_image_view);
        if (currentWall.getImageFile() != null) {
            Ion.with(getContext())
                    .load(currentWall.getImageFile().getUrl())
                    .withBitmap()
                    .centerCrop()
                    .intoImageView(imageView);
        }

        TextView offerTitleTextView = (TextView)rootView.findViewById(R.id.wall_offer_title_text_view);
        Offer offer = (Offer)currentWall.getOfferPointer();
        offerTitleTextView.setText(offer.getTitle());

        TextView statusTitleTextView = (TextView)rootView.findViewById(R.id.wall_status_text_view);
        if ("android.intent.action.PACKAGE_ADDED".equals(currentWall.getStatus())) {
            statusTitleTextView.setText("完成");
        }
        if ("Tap Want to Install".equals(currentWall.getStatus())) {
            statusTitleTextView.setText("進行中");
        }

        return rootView;
    }
}
