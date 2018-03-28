package com.example.tukicmilan.auctions.milan.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by asski on 23-May-17.
 */

//don't use, it is for example!
public class ItemsInListViewAdapter extends ArrayAdapter<Item> {
    private Context context;

    public ItemsInListViewAdapter(@NonNull Activity context, List<Item> items) {
        super(context, 0, items);
        this.context = context;
    }

    public void refresh(List<Item> filtered) {
        this.clear();
        this.addAll(filtered);
        notifyDataSetChanged();
    }

        @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // find root view
        View listItemView = convertView;

        // if it's null, inflate it
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // find the current Item object
        Item item = getItem(position);
        //Item item = items.get(position);

        // TODO: Fill the item with data
        TextView textViewName = (TextView) listItemView.findViewById(R.id.list_name);
        textViewName.setText(item.getName());

        TextView textViewDescription = (TextView) listItemView.findViewById(R.id.list_description);
        textViewDescription.setText(item.getDescription());


        ImageView imageView = (ImageView) listItemView.findViewById(R.id.list_item_image);
        // TODO: Uzmi ID slike iz itema
        //imageView.setBackgroundResource(Integer.parseInt(item.getPicture()));
        Picasso.with(context.getApplicationContext()).load(item.getPicture()).error(R.drawable.esplash).into(imageView);


        CheckBox isSold = (CheckBox) listItemView.findViewById(R.id.list_item_sold);
        isSold.setChecked(item.isSold());

        return listItemView;
    }
}
