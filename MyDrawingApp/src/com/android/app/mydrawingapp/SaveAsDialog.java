package com.android.app.mydrawingapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SaveAsDialog extends DialogFragment {
	EditText etTitle;
	Button btnSave;
	
	public SaveAsDialog() {
	}
	
	public interface SaveAsDialogListener {
		void saveAs(String title);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.save_as, container);
		getDialog().setTitle("Set Drawing name:");
		etTitle = (EditText) v.findViewById(R.id.editText1);
		btnSave = (Button) v.findViewById(R.id.btnSaveDrawing);
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SaveAsDialogListener listener = (SaveAsDialogListener) getActivity();
				listener.saveAs(etTitle.getText().toString());
				dismiss();
			}
		});
		return v;
	}

}
