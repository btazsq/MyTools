package com.example.testtools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtools.network.image.MyImage;

public class TestRecyclerAdpater extends RecyclerView.Adapter<TestRecyclerAdpater.TestHoldet>{
    @NonNull
    @Override
    public TestHoldet onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_1, parent, false);
        return new TestHoldet(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TestHoldet holder, int position) {
        MyImage.with()
                .setPreView(R.mipmap.ic_launcher)
                .load("https://s2.ax1x.com/2020/02/08/1Wnh36.png")
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class TestHoldet extends RecyclerView.ViewHolder{

        ImageView imageView;

        public TestHoldet(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_test);
        }

    }

}
