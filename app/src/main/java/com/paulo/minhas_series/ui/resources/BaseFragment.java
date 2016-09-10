package com.paulo.minhas_series.ui.resources;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;

/**
 * Created by Paulo Henrique on 04/09/2016.
 */
public abstract class BaseFragment extends Fragment {

    public abstract int getLayoutResourceId();
    public abstract void loadViews();
    public abstract void manageIntent();
    public abstract void setupViews(View rootView);

    public Realm realm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        realm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResourceId(), container, false);

        setupViews(rootView);
        manageIntent();
        loadViews();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (realm == null)
            realm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (realm != null)
            realm.close();
    }
}
