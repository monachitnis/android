package com.myandroid.example.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		
		Intent i = getIntent();
		ImageResult ir = (ImageResult) i.getSerializableExtra("result");
		SmartImageView iv = (SmartImageView) findViewById(R.id.ivResult);
		iv.setImageUrl(ir.getFullUrl());
		
	}
}
