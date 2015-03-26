package tw.soleil.offerwall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import tw.soleil.core.parse.Action;

import java.util.List;

/**
 * Created by edward_chiang on 15/1/5.
 */
public class MissionAdapter extends ArrayAdapter<Action> {


    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public MissionAdapter(Context context, int resource, List<Action> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
