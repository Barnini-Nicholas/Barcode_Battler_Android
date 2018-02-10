package com.mbds.barcode_battler_android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mbds.barcode_battler_android.MainActivity;
import com.mbds.barcode_battler_android.R;
import com.mbds.barcode_battler_android.Service.TagLog;
import com.mbds.barcode_battler_android.adapter.ListViewCreaturesAdapter;

/**
 * Created by Karl on 10/02/2018.
 */

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.about, container, false);

        view.findViewById(R.id.barnini).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Barnini aka le développeur web", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
