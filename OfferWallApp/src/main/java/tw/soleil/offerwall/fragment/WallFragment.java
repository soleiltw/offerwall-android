package tw.soleil.offerwall.fragment;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import tw.soleil.offerwall.OfferWall;
import tw.soleil.offerwall.R;
import tw.soleil.offerwall.adapter.WallAdapter;
import tw.soleil.core.parse.Action;
import tw.soleil.core.parse.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edward_chiang on 15/1/1.
 */
public class WallFragment extends Fragment {
    public static final String TAG = "WallFragment";

    private List<Wall> wallList;

    private WallAdapter wallAdapter;

    private static final int LOGIN_ACTIVITY_CODE = 99;

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

                if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().isAuthenticated()) {
                    Wall currentWall = wallList.get(position);

                    final Action action = new Action();
                    action.setType("Tap Want to Install");
                    action.setUserPointer(ParseUser.getCurrentUser());
                    action.setValue(currentWall.getPackageName());
                    action.saveEventually();

                    Intent openURL = new Intent(Intent.ACTION_VIEW);
                    openURL.setData(Uri.parse(currentWall.getActionURL()));
                    startActivity(openURL);
                } else {
                    Toast.makeText(getActivity(), "Login your account first.", Toast.LENGTH_SHORT).show();
                    ParseLoginBuilder builder = new ParseLoginBuilder(getActivity());
                    startActivityForResult(builder.build(), LOGIN_ACTIVITY_CODE);
                }


            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadObjects();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_ACTIVITY_CODE && resultCode == Activity.RESULT_OK) {
            loadObjects();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadObjects();
    }

    private void loadActions() {

        if (ParseUser.getCurrentUser().isAuthenticated() && ParseUser.getCurrentUser().getObjectId() !=null &&
                !"null".equalsIgnoreCase(ParseUser.getCurrentUser().getObjectId())) {
            ParseQuery<Wall> wallParseQuery = ParseQuery.getQuery(Wall.class);

            ParseQuery<Action> actionParseQuery = ParseQuery.getQuery(Action.class);
            actionParseQuery.whereMatchesKeyInQuery("value", "packageName", wallParseQuery);
            actionParseQuery.whereEqualTo("userPointer", ParseUser.getCurrentUser());

            Log.i(OfferWall.TAG, "User id: " + ParseUser.getCurrentUser().getObjectId());
            actionParseQuery.orderByDescending("createdAt");
            actionParseQuery.findInBackground(new FindCallback<Action>() {
                @Override
                public void done(List<Action> actions, ParseException e) {
                    Log.d(OfferWall.TAG, "Find in background");
                    if  (actions !=null && actions.size() > 0) {
                        Log.i(OfferWall.TAG, "We found: " + actions);

                        boolean hasChange = false;
                        for (Action eachAction : actions) {
                            for (Wall eachWall : wallList) {

                                if (eachWall.getPackageName().equals(eachAction.getValue())){
                                    if (eachWall.getStatus() == null) {
                                        eachWall.setStatus(eachAction.getType());
                                        hasChange = true;
                                    }
                                    else if (
                                            eachAction.getType().startsWith("android.") &&
                                                    eachAction.getType().endsWith("ADDED")) {
                                        eachWall.setStatus(eachAction.getType());
                                        hasChange = true;
                                    }
                                }
                            }

                        }
                        if (hasChange) {
                            wallAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Login your account first.", Toast.LENGTH_SHORT).show();
            ParseLoginBuilder builder = new ParseLoginBuilder(getActivity());
            startActivityForResult(builder.build(), LOGIN_ACTIVITY_CODE);
        }


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

                    loadActions();
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
