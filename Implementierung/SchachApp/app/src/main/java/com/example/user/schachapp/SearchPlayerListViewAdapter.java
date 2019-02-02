package com.example.user.schachapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SearchPlayerListViewAdapter extends BaseAdapter{
    ClientSocket cs;
    Context mContext;
    LayoutInflater inflater;
    List<String> modellist;
    ArrayList<String> arrayList;

    //constructor
    public SearchPlayerListViewAdapter(Context context, List<String> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<String>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder{
        TextView mTitleTv;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.user_name, null);

            //locate the views in user_name.xml
            holder.mTitleTv = view.findViewById(R.id.userNames);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder)view.getTag();
        }
        //set the results into textviews
        holder.mTitleTv.setText(modellist.get(postition));

        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Iterator<String> it = arrayList.iterator();
                String player = "";
                while (it.hasNext()) {
                    //code later
                    player = it.next();
                    if (modellist.get(postition).equals(player)) {
                        Intent intent = new Intent(mContext, ChallengeActivity.class);
                        intent.putExtra("clickedPlayer", player);
                        mContext.startActivity(intent);
                    }
                }
            }
        });
        return view;
    }

    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length()==0){
            modellist.addAll(arrayList);
        }
        else {
            for (int i = 0; i < arrayList.size(); i++){
                String model = arrayList.get(i);
                if (model.toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }

}