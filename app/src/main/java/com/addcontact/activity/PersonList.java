package com.addcontact.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.addcontact.R;
import com.addcontact.adapter.ApplicationAdapter;
import com.addcontact.application.cAapplication;
import com.addcontact.itemanimator.CustomItemAnimator;
import com.addcontact.models.DataObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 15/02/2015.
 */
public class PersonList extends Fragment {

    private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private ApplicationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewFragement";
    private ArrayList<DataObject> _list;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2,container,false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.contentView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new CustomItemAnimator());
        _list= cAapplication.getInstance().getDBAdapter().getAllContact();
        mAdapter = new ApplicationAdapter(_list, R.layout.card_view_row,getActivity());
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setItemAnimator(new CustomItemAnimator());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(LOG_TAG, " mSwipeRefreshLayout onRefresh() ");
                LoadAsyncTask saveTask = new LoadAsyncTask();
                saveTask.execute();
            }
        });

        ((ApplicationAdapter) mAdapter).setOnItemClickListener(new ApplicationAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Log.i(LOG_TAG, " Clicked on Item " + position);
                    Intent i = new Intent(getActivity(), DetailActivity.class);
                    i.putExtra("UserId", _list.get(position).getId());
                    getActivity().startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
                }
        });

        return v;
    }

    private void loadData()
    {
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //_list.clear();
        //_list= cAapplication.getInstance().getDBAdapter().getAllContact();
        //mAdapter = new MyRecyclerViewAdapter(_list);
        //mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /*if(cAapplication.getDataObject()!=null){
            DataObject dataObject= cAapplication.getDataObject();
            _list.add(dataObject);
            mAdapter.notifyItemInserted(_list.size()-1);
            mAdapter.notifyDataSetChanged();
        }*/

        LoadAsyncTask saveTask = new LoadAsyncTask();
        saveTask.execute();

    }

    class LoadAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected  void onPreExecute(){
            mAdapter.clearApplications();
        }

        @Override
        protected String doInBackground(Void... params) {
            _list= cAapplication.getInstance().getDBAdapter().getAllContact();
             return "success";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(_list.size()==0){
                final Toast toast = Toast.makeText(getActivity(), "No Person Added", Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 3000);
            }
            if(result.equals("success")){
                 mAdapter.addApplications(_list);
            }

            if (mSwipeRefreshLayout!=null) {
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.destroyDrawingCache();
                mSwipeRefreshLayout.clearAnimation();
                //cAapplication.setDataObject(null);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.e("DEBUG", "onCreate() of PersonListFragment");
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of PersonListFragment");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of PersonListFragment");
        super.onPause();

        /*if (mSwipeRefreshLayout!=null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.destroyDrawingCache();
            mSwipeRefreshLayout.clearAnimation();
        }*/
    }
}