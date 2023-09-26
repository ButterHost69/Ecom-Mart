package com.example.sihv2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sihv2.Interface.ItemClickListner;
import com.example.sihv2.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView product_name;
    public TextView product_description;
    public TextView product_price;
    ImageView product_image;

    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);

        product_image = (ImageView) itemView.findViewById(R.id.product_image);

        product_name = (TextView) itemView.findViewById(R.id.product_name);
        product_description = (TextView) itemView.findViewById(R.id.product_description);
        product_price= (TextView) itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
