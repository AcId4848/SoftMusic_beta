package com.example.softmusic_beta.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class StateFragmentAdapter extends FragmentStateAdapter {
    private final LinkedHashMap<Class<?>, Fragment> m_vItems;

    private final FragmentManager m_vFragmentManager;

    public void addFragment(Class<?> fragmentType) {
        try {
            Constructor<?> constr = fragmentType.getDeclaredConstructor();
            Fragment frag = (Fragment) constr.newInstance();
            this.m_vItems.put(fragmentType, frag);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public StateFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

        this.m_vItems = new LinkedHashMap<>();
        this.m_vFragmentManager = fragmentManager;
    }

    public void addFragment(Object fragment) {
        try {
            if (!(fragment instanceof Fragment))
                throw new Exception("Not instance of Fragment!!!");

            Class<?> clasz =  Class.forName(fragment.getClass().getName());
            this.m_vItems.put(clasz, (Fragment) fragment);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addFragment(Class<?> fragmentType, Object... params) {
        try {
            if (params != null && params.length > 0) {
                Class<?>[] classes = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    Object obj = params[i];
                    classes[i] = Class.forName(obj.getClass().getName());
                }

                Constructor<?> constructor = fragmentType.getDeclaredConstructor(classes);
                Fragment fragment = (Fragment) constructor.newInstance(params);
                this.m_vItems.put(fragmentType, fragment);
            }
            else {
                addFragment(fragmentType);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return (Fragment) this.m_vItems.values().toArray()[position];
    }

    @Override
    public int getItemCount() {
        return this.m_vItems.size();
    }
}
