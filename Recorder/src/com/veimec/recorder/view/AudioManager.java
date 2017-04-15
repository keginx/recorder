package com.veimec.recorder.view;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.R.string;
import android.media.MediaRecorder;
import android.provider.MediaStore.Audio.Media;

public class AudioManager {
	private MediaRecorder mMediaRecorder;
	private String mDir;
	private String mCurrentFilePath;
	private static AudioManager mInstance;

	private boolean isPrepared;

	private AudioManager(String dir) {
		mDir = dir;
	}

	/**
	 * 回调准备完毕
	 */
	public interface AudioStateListener {
		void wellPrepared();
	}

	public AudioStateListener mListener;

	public void setOnAudioStateListener(AudioStateListener listener) {
		mListener = listener;
	}

	public static AudioManager getInstance(String dir) {
		if (mInstance == null) {
			synchronized (android.media.AudioManager.class) {
				if (mInstance == null) {
					mInstance = new AudioManager(dir);

				}
			}
		}
		return mInstance;
	}

	public void prepareAudio() {
		try {

			isPrepared = false;
			File dir = new File(mDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String fileName = generateFileNAme();
			File file = new File(dir, fileName);
			mCurrentFilePath = file.getAbsolutePath();
			mMediaRecorder = new MediaRecorder();
			// 设置输出文件
			mMediaRecorder.setOutputFile(file.getAbsolutePath());
			// 设置音频源为麦克
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			// 设置音频格式
			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
			// 设置音频编码为AMR_NB
			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mMediaRecorder.prepare();
			mMediaRecorder.start();
			// 准备结束
			isPrepared = true;

			if (mListener != null) {
				mListener.wellPrepared();
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 随机生成文件名称
	 * 
	 * @return
	 */
	private String generateFileNAme() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString() + ".amr";

	}

	public int getVoiceLevel(int maxLevel) {
		if (isPrepared) {
			try {
				// mMediaRecorder.getMaxAmplitude() 1-32767
				return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return 1;

	}

	public void release() {
		mMediaRecorder.stop();
		mMediaRecorder.release();
		mMediaRecorder = null;
	}

	public void cancel() {
		release();
		if (mCurrentFilePath != null) {
			File file = new File(mCurrentFilePath);
			file.delete();
			mCurrentFilePath = null;
		}

	}

	public String getCurrentFilePath() {
		// TODO Auto-generated method stub
		return mCurrentFilePath;
	}
}
