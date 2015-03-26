package tw.soleil.test.demoapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tw.soleil.offerwall.core.activity.OfferWallActivity;
import tw.soleil.test.demoapp.R;

/**
 * Created by weitang114 on 15/3/26.
 */
public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = (Button) view.findViewById(R.id.btn_offerwall);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), OfferWallActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }
}
