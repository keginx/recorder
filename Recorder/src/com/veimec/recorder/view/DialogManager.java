package com.veimec.recorder.view;

import com.imooc.recorder.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogManager {
	private Dialog mDialog;
	private ImageView mIcon;
	private ImageView mVoice;
	private TextView mLabel;
	private Context mContext;

	public DialogManager(Context context) {
		mContext = context;
	}

	// 显示录音对话框
	public void showRecordingDialog() {
		mDialog = new Dialog(mContext, R.style.Theme_AduioDialog);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.dialog_recorder, null);
		mDialog.setContentView(view);

		mIcon = (ImageView) mDialog.findViewById(R.id.id_recorder_dialog_icon);
		mVoice = (ImageView) mDialog.findViewById(R.id.id_recorder_dialog_voice);
		mLabel = (TextView) mDialog.findViewById(R.id.id_recorder_dialog_label);
		mDialog.show();
	}

	public void recording() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.VISIBLE);
			mLabel.setVisibility(View.VISIBLE);
			
			mIcon.setImageResource(R.drawable.recorder);
			mLabel.setText("手指上划,取消发送");
		}
	}

	// 显示想取消对话框
	public void wantToCancel() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.GONE);
			mLabel.setVisibility(View.VISIBLE);
			mIcon.setImageResource(R.drawable.cancel);
			mLabel.setText("松开手指,取消发送");
		}

	}

	// 显示录音太短对话框
	public void tooShort() {
		if (mDialog != null && mDialog.isShowing()) {
			mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.GONE);
			mLabel.setVisibility(View.VISIBLE);
			mIcon.setImageResource(R.drawable.voice_to_short);
			mLabel.setText("录音时间过短");
		}
	}

	// 隐藏对话框
	public void dimissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
			mDialog = null;

		}
	}

	// 更新对话框上的音量的标志
	// 通过level更新voice上的图片level 1-7
	public void updateVoiceLevel(int level) {
		if (mDialog != null && mDialog.isShowing()) {
			/*mIcon.setVisibility(View.VISIBLE);
			mVoice.setVisibility(View.VISIBLE);
			mLabel.setVisibility(View.VISIBLE);*/
			
			int resId = mContext.getResources().getIdentifier("v" + level, "drawable", mContext.getPackageName());
			mVoice.setImageResource(resId);
		}
	}
}
