package tw.soleil.offerwall.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import tw.soleil.offerwall.Adapter.WallAdapter;
import tw.soleil.offerwall.R;
import tw.soleil.offerwall.object.parse.Action;
import tw.soleil.offerwall.object.parse.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edward_chiang on 15/1/1.
 */
public class WallFragment extends Fragment {

    private List<Wall> wallList;

    private WallAdapter wallAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wallList = new ArrayList<>();
        wallAdapter = new WallAdapter(getActivity(), R.layout.cell_wall, wallList);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wall, container, false);

        ListView wallListView = (ListView)rootView.findViewById(R.id.wall_list_view);
        wallListView.setAdapter(wallAdapter);

        wallListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Wall currentWall = wallList.get(position);

                Action action = new Action();
                action.setType("Tap Want to Install");
                action.setValue(currentWall.getPackageName());
                action.saveEventually();

                Intent openURL = new Intent(Intent.ACTION_VIEW);
                openURL.setData(Uri.parse(currentWall.getActionURL()));
                startActivity(openURL);

            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadObjects();
    }

    private void loadObjects() {
        ParseQuery<Wall> wallParseQuery = ParseQuery.getQuery(Wall.class);
        wallParseQuery.include("offerPointer");
        wallParseQuery.findInBackground(new FindCallback<Wall>() {
            @Override
            public void done(List<Wall> walls, ParseException e) {
                if (e == null && walls != null) {
                    wallList.clear();
                    wallList.addAll(walls);
                    wallAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reload:
                loadObjects();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
