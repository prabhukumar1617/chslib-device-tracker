package com.chs.devicetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chsLib.deviceTracker.model.ChsSpeaker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
    private final String TAG = MainActivity.class.getSimpleName();

    private Button mStart, mStop, mClear;
    RecyclerView listView;
    private MainPresenter presenter;
    private SpeakerListAdapter speakerListAdapter;
    private List<ChsSpeaker> speakerList = new ArrayList<ChsSpeaker>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenterImpl(this);
        speakerListAdapter = new SpeakerListAdapter(this);
        presenter.setView(this);
    }

    @Override
    public void initViews() {
        mStart = (Button) findViewById(R.id.button_start);
        mStop = (Button) findViewById(R.id.button_stop);
        mClear = (Button) findViewById(R.id.button_clear);
        listView = (RecyclerView) findViewById(R.id.recycler_View);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.addItemDecoration(new RecyclerViewItemDecorator(this, 20));
        listView.setNestedScrollingEnabled(false);
        listView.setAdapter(speakerListAdapter);
    }

    @Override
    public void addListeners() {
        if (mStart != null) {
            mStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: start Button : ");
                    presenter.startSpeakerDiscovery();
                }
            });
        }

        if (mStop != null) {
            mStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: stop Button : ");

                    presenter.stopSpeakerDiscovery();
                }
            });
        }

        if (mClear != null) {
            mClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: clear Button : ");
                    presenter.clearSpeakerList();
                }
            });
        }
    }

    private void clearSpeakerList() {
    }

    @Override
    public void updateSpeakerList(final List<ChsSpeaker> speakerList) {
        Log.d(TAG, "updateSpeakerList: -------------------  speakerList : " + speakerList.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                speakerListAdapter.refreshUI(speakerList);

            }
        });
    }
}
