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

/**
 * The type Search player list view adapter.
 * This class helps the SearchPlayerActivity.
 */
public class SearchPlayerListViewAdapter extends BaseAdapter{
    /**
     * The Cs.
     */
    ClientSocket cs;
    /**
     * The M context.
     */
    Context mContext;
    /**
     * The Inflater.
     */
    LayoutInflater inflater;
    /**
     * The Modellist.
     */
    List<String> modellist;
    /**
     * The Array list.
     */
    ArrayList<String> arrayList;

    /**
     * Instantiates a new Search player list view adapter.
     *
     * @param context   the context
     * @param modellist the modellist where the user-names are.
     */

    public SearchPlayerListViewAdapter(Context context, List<String> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<String>();
        this.arrayList.addAll(modellist);
    }

    /**
     * The type View holder.
     */
    public class ViewHolder{
        /**
         * The M title tv.
         */
        private TextView mTitleTv;
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

                // changes in ChallengeActivity, when a user-name is clicked.
                Iterator<String> it = arrayList.iterator();
                String player = "";
                while (it.hasNext()) {

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

    /**
     * A Filter that filters the user-names.
     *
     * @param charText the char text
     */
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