package com.myandriod.example.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	EditText enterTotal, split;
	SeekBar sk;
	int percent = 0, splitVal = 1;
	float billAmt;
	TextView tvTipPercent, tvTotalTip, tvSplitTip;
	String dollar = " $";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvTotalTip = (TextView) findViewById(R.id.tvTotalTip);
		tvTotalTip.setText("0.0" + dollar); // default
		tvSplitTip = (TextView) findViewById(R.id.tvSplitTip);
		tvSplitTip.setText(tvTotalTip.getText()); // default

		sk = (SeekBar) findViewById(R.id.skSelectPercent);
		setupTipPercentListener();
		split = (EditText) findViewById(R.id.etSplitPeople);
		split.setText(splitVal + ""); // default
		setupSplitListener();

		enterTotal = (EditText) findViewById(R.id.etEnterTotal);
		setupCheckAmtListener();
	}

	private void setupCheckAmtListener() {
		enterTotal.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0) {
					billAmt = Float.parseFloat(s.toString());
				}
				// update tips
				float tip = billAmt * percent / 100;
				tvTotalTip.setText(tip + dollar);
				tvSplitTip.setText((tip/splitVal) + dollar);
			}

			@Override
			public void afterTextChanged(Editable s) {				
			}
			
		});
		
	}

	private void setupTipPercentListener() {
		sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				percent = progress;
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// get entered bill amount
				String amtStr = enterTotal.getText().toString();
				if (amtStr.length() == 0 || amtStr.isEmpty())
					billAmt = 0;
				else
					billAmt = Float.parseFloat(amtStr);

				// display it
				tvTipPercent = (TextView) findViewById(R.id.tvDisplayPercent);
				tvTipPercent.setText(percent + " %"); // same as above

				// calculate total tip
				float tip = billAmt * percent / 100;
				tvTotalTip.setText(tip + dollar);
				tip = billAmt * percent / splitVal / 100;
				tvSplitTip.setText(tip + dollar);

			}
		});

	}

	/**
	 * Calculate tip per head
	 */
	private void setupSplitListener() {
		split.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0) {
					splitVal = Integer.parseInt(s.toString());
					if (splitVal == 1)
						tvSplitTip.setText(tvTotalTip.getText());
					else {
						if (splitVal > 0) {
							float tip = billAmt * percent / splitVal / 100;
							tvSplitTip.setText(tip + dollar);
						}
					}
				}

			}

			@Override
			public void afterTextChanged(Editable s) {
			}

		});

	}
}
