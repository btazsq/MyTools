package com.example.testtools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testtools.network.image.MyImage;

public class ViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_1, container, false);
        MyImage.with()
                .setPreView(R.mipmap.ic_launcher)
                .load("https://s2.ax1x.com/2020/02/08/1Wnh36.png")
                .into(view.findViewById(R.id.image_test))
                .addBuffer();
        return view;
    }
}
