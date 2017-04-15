package com.veimec.recorder;

import java.util.ArrayList;
import java.util.List;

import com.imooc.recorder.R;
import com.veimec.recorder.view.AudioRecorderButton;
import com.veimec.recorder.view.AudioRecorderButton.AudioFinishRecorderListener;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView mListView;
	private ArrayAdapter<Recorder> mAdapter;
	private List<Recorder> mData = new ArrayList<Recorder>();
	private AudioRecorderButton mAudioRecorderButton;
	private View mAnimView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.id_listview);
		mAudioRecorderButton = (AudioRecorderButton) findViewById(R.id.id_recorder_button);
		mAudioRecorderButton.setAudioFinishRecorderListener(new AudioFinishRecorderListener() {

			@Override
			public void onFinish(float seconds, String filePath) {
				// TODO Auto-generated method stub
				Recorder recorder = new Recorder(seconds, filePath);
				mData.add(recorder);
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(mData.size() - 1);
			}
		});

		mAdapter = new RecorderAdapter(this, mData);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(mAnimView!=null){
					mAnimView.setBackgroundResource(R.drawable.adj);
				}
				
				// 播放动画声波图标 利用帧动画
				mAnimView = view.findViewById(R.id.id_recorder_anim);
				mAnimView.setBackgroundResource(R.drawable.play_anim);

				AnimationDrawable anim = (AnimationDrawable) mAnimView.getBackground();
				anim.start();
				// 播放音频
				MediaManager.playSound(mData.get(position).filePath, new MediaPlayer.OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						mAnimView.setBackgroundResource(R.drawable.adj);
					}
				});
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		MediaManager.pause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MediaManager.resume();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MediaManager.release();
	}
	class Recorder {
		float time;
		String filePath;

		public Recorder(float time, String filePath) {
			super();
			this.time = time;
			this.filePath = filePath;
		}

		public float getTime() {
			return time;
		}

		public void setTime(float time) {
			this.time = time;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

	}
}
