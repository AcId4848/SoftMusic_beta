package com.example.softmusic_beta.ui.adapters.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.softmusic_beta.R;
import com.example.softmusic_beta.ui.adapters.viewholders.BaseViewHolder;
import com.example.softmusic_beta.ui.adapters.viewholders.SongViewHolder;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;

public class BaseViewHelper {
    private final static LinkedHashMap<Class<?>, Integer> m_vLayoutIds = new LinkedHashMap<>();

    static {
        m_vLayoutIds.put(SongViewHolder.class, R.layout.item_allmusic_song_view);
    }

    private static int getLayoutId(Class<?> viewHolder) {
        return m_vLayoutIds.getOrDefault(viewHolder, -1);
    }

    public static <T extends BaseViewHolder> T onCreateViewHolder(Class<T> viewHolder, ViewGroup parent) {
        T result;
        try {
            View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewHolder), parent, false);
            Constructor<?> constructor = viewHolder.getDeclaredConstructor(View.class);
            return (T) constructor.newInstance(view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
