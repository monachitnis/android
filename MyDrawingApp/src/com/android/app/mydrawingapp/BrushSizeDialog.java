package com.android.app.mydrawingapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class BrushSizeDialog extends DialogFragment {
	ImageButton small, medium, large;
	
	public interface BrushSizeDialogListener {
		void passSize(BRUSH_SIZE size);
	}
	
	public BrushSizeDialog() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.brush_size, container);
		getDialog().setTitle("Brush Size:");
		small = (ImageButton) v.findViewById(R.id.btnSmallBrush);
		medium = (ImageButton) v.findViewById(R.id.btnMediumBrush);
		large = (ImageButton) v.findViewById(R.id.btnLargeBrush);
		small.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BrushSizeDialogListener listener = (BrushSizeDialogListener) getActivity();
				listener.passSize(BRUSH_SIZE.SMALL);
				dismiss();
			}
		});
		medium.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BrushSizeDialogListener listener = (BrushSizeDialogListener) getActivity();
				listener.passSize(BRUSH_SIZE.MEDIUM);
				dismiss();
			}
		});
		large.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BrushSizeDialogListener listener = (BrushSizeDialogListener) getActivity();
				listener.passSize(BRUSH_SIZE.LARGE);
				dismiss();
			}
		});
		return v;
	}

}
