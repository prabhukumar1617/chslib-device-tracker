package com.chs.devicetracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chsLib.deviceTracker.model.ChsSpeaker;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by prabhu on 14/2/18.
 */

public class SpeakerListAdapter extends RecyclerView.Adapter<SpeakerListAdapter.MyViewHolder> {
    private final String TAG = SpeakerListAdapter.class.toString();
    private List<ChsSpeaker> speakerList = new ArrayList<ChsSpeaker>();
    private MainActivity speakerListActivity;

    public SpeakerListAdapter(Context context) {
        speakerListActivity = (MainActivity) context;
    }

    @Override
    public SpeakerListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_speaker_list_main, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ChsSpeaker currentSpeaker = speakerList.get(position);
        final String name = currentSpeaker.getName();
        holder.mSpeakerName.setText(name);
        if (currentSpeaker.isOnline())
            holder.mIconStatus.setImageResource(R.drawable.local_available);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(wifiListActivity, WifiConfigActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("SSID", ssid);
                intent.putExtras(bundle);
                wifiListActivity.startActivity(intent);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return speakerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mSpeakerName;
        private ImageView mIconStatus;

        public MyViewHolder(View view) {
            super(view);
            mSpeakerName = (TextView) view.findViewById(R.id.text_view_artist_name);
            mIconStatus = (ImageView) view.findViewById(R.id.ic_status);
        }
    }

    public void refreshUI(List<ChsSpeaker> speakerList) {
        Log.d(TAG, "refreshUI: -----------------------------called" + speakerList.toString());
        this.speakerList = speakerList;
        notifyDataSetChanged();
    }


}
