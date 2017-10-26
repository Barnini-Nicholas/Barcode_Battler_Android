package com.mbds.barcode_battler_android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mbds.barcode_battler_android.R;
import com.mbds.barcode_battler_android.adapter.ListViewCreaturesAdapter;

public class CreaturesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_creatures, container, false);

        ListView lv = (ListView) view.findViewById(R.id.listViewCreature);
        lv.setAdapter(new ListViewCreaturesAdapter(getContext()));

        return view;
    }

}
