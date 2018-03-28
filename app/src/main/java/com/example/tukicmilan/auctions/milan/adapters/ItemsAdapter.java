package com.example.tukicmilan.auctions.milan.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.activities.ItemActivity;
import com.example.tukicmilan.auctions.milan.data.BidContract;
import com.example.tukicmilan.auctions.milan.data.ContentProvider;
import com.example.tukicmilan.auctions.milan.data.DAO;
import com.example.tukicmilan.auctions.milan.data.DatabaseHelper;
import com.example.tukicmilan.auctions.milan.data.ItemContract;
import com.example.tukicmilan.auctions.milan.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.os.Build.ID;
import static com.example.tukicmilan.auctions.milan.data.ItemContract.ItemEntry._ID;
import static com.example.tukicmilan.auctions.milan.data.ItemContract.PATH_ITEM;

/**
 * Created by TukicMilan on 2/21/2018.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;


    public interface OnItemClickListener {
        void onItemClick(Item item, int position);
    }

    Context context;
    private List<Item> itemListR;
    private OnItemClickListener listener;

    public void setOnClikListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // private OnItemClickListener listener;
        public TextView name, description;
        public ImageView picture;
        public CheckBox sold;

        public MyViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_name);
            description = (TextView) itemView.findViewById(R.id.list_description);
            picture = (ImageView) itemView.findViewById(R.id.list_item_image);
            sold = (CheckBox) itemView.findViewById(R.id.list_item_sold);
            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Toast.makeText(v.getContext(), "Position is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(itemView.getContext());
                    alertDialogBuilder.setTitle(null);
                    {
                        alertDialogBuilder
                                .setTitle("Confirm Delete...").setIcon(R.drawable.del)
                                .setMessage("Are you sure you want delete this?")
                                .setCancelable(true) // True allows you to use the back button to exit the dialog, false does not
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //your deleting code
                                        removeItem(getAdapterPosition());
                                       // deleteItem(getAdapterPosition());
                                        dialog.dismiss();
                                    }

                                })

                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();

                                    }
                                })
                                .create();

                    }
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    return false;
                }
            });
        }

        private void removeItem(int pos) {
            itemListR.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, getItemCount());
        }

        private void deleteFromDB() {

            ContentValues itemValues = new ContentValues();


           // context.getContentResolver().delete(ItemContract.ItemEntry.CONTENT_URI,
                    //ItemContract.ItemEntry.COLUMN_NAME + " = ?",
                  //  new String[]{itemValues.getAsString(song.getTitle())});

            //switch off button
            //imgViewFavButton.setImageResource(android.R.drawable.btn_star_big_off);
        }

        public void deleteItem(int position){
            database = dbHelper.getWritableDatabase();
            database.execSQL("DELETE FROM " + PATH_ITEM + " WHERE " +
                    _ID + " = " + position + ";");

            database.close();

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Item item = itemListR.get(position);
            if(listener != null){
                listener.onItemClick(item, position);
            }
        }
    }

    public ItemsAdapter(@NonNull Activity context, List<Item> itemListR){
        this.context = context;
        this.itemListR = itemListR;
    }

    @Override
    public ItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsAdapter.MyViewHolder holder, int position) {
        //holder.bind(itemListR.get(position), listener);
        Item item = itemListR.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        //holder.picture.setText(item.getPicture());
        Picasso.with(context.getApplicationContext()).load(item.getPicture()).error(R.drawable.esplash).into(holder.picture);
        holder.sold.setChecked(item.isSold());
    }

    @Override
    public int getItemCount() {
        return itemListR.size();
    }

    public void refresh(List<Item> itemFiltered) {
        this.itemListR = itemFiltered;
        //this.itemListR.clear();
        //this.itemListR.addAll(itemFiltered);
        notifyDataSetChanged();
    }
}
