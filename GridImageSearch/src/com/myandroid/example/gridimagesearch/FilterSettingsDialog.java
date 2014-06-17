package com.myandroid.example.gridimagesearch;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FilterSettingsDialog extends DialogFragment {
	Spinner spSize;
	Spinner spColor;
	Spinner spType;
	EditText etSite;
	Button btSave;
	
	public interface FilterSettingsDialogListener {
		void passSettings(Filters f);
	}
	
	public FilterSettingsDialog() {
	}

	public static FilterSettingsDialog newInstance(String title) {
		FilterSettingsDialog frag = new FilterSettingsDialog();
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_settings, container);
		String title = getArguments().getString("title", "Image Filters");
		getDialog().setTitle(title);
		setupViews(v);
		btSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FilterSettingsDialogListener listener = (FilterSettingsDialogListener) getActivity();
				Filters f = new Filters(spSize.getSelectedItem().toString(), spColor
						.getSelectedItem().toString(), spType.getSelectedItem()
						.toString(), etSite.getText().toString());
				listener.passSettings(f);
				dismiss();
			}
			
		});
		return v;
	}
	
	private void setupViews(View view) {
		spSize = (Spinner) view.findViewById(R.id.spSize);
		spColor = (Spinner) view.findViewById(R.id.spColor);
		spType = (Spinner) view.findViewById(R.id.spType);
		etSite = (EditText) view.findViewById(R.id.etSite);
		btSave = (Button) view.findViewById(R.id.btSaveSettings);
	}

}
