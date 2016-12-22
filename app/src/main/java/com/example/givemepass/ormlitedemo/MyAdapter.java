package com.example.givemepass.ormlitedemo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rick.wu on 2016/12/13.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<User> mData;
    private Context mContext;
    private final String[] itemList = {"編輯", "刪除"};
    private DBHelper mDbHelper;
    public MyAdapter(Context mContext, DBHelper dbHelper) {
        this.mContext = mContext;
        mData = new ArrayList<>();
        mDbHelper = dbHelper;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);
        }
    }

    public void setData(List<User> data){
        mData = data;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(mData.get(position).getName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(mContext)
                .setItems(itemList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final User user = mData.get(position);
                        switch(which){
                            case 0:
                                final View item = LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);
                                final EditText editText = (EditText) item.findViewById(R.id.edit_text);
                                editText.setText(user.getName());
                                new AlertDialog.Builder(mContext)
                                    .setTitle("編輯資料")
                                    .setView(item)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String name = editText.getText().toString();
                                            if(name == null || name.equals("")){
                                                Toast.makeText(mContext, "請輸入文字...", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            user.setName(name);
                                            mDbHelper.editData(user);
                                            mData.set(position, user);
                                            Toast.makeText(mContext, "修改成功!", Toast.LENGTH_SHORT).show();
                                            notifyItemChanged(position);
                                        }
                                    })
                                    .show();

                                break;
                            case 1:
                                mDbHelper.delData(user);
                                mData.remove(position);
                                notifyItemChanged(position);
                                break;
                        }

                    }
                })
                .show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
