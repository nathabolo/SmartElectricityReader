package com.example.android.electricreader;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.android.electricreader.R.id.container;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private int position = 0;
    private DrawerLayout mDrawerLayout;
    private ListView navList;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private Object AdapterView;


    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();

            //Determine if the drawer is closed or opened.  Open drawer or close drawer
            if (id == R.id.action_settings) {
                return true;
            } else if (id == android.R.id.home) {

                if (mDrawerLayout.isDrawerOpen(navList)) {
                    mDrawerLayout.closeDrawer(navList);
                } else {
                    mDrawerLayout.openDrawer(navList);
                }
            }


            return super.onOptionsItemSelected(item);

        }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
