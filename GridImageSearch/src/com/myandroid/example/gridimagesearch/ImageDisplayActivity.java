package com.myandroid.example.gridimagesearch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.loopj.android.image.SmartImageTask.OnCompleteListener;
import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {
	SmartImageView iv;
	ShareActionProvider miShareAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);

		Intent i = getIntent();
		ImageResult ir = (ImageResult) i.getSerializableExtra("result");
		iv = (SmartImageView) findViewById(R.id.ivResult);

		// advanced story - sending image to other app
		iv.setImageUrl(ir.getFullUrl(), new OnCompleteListener() {
			@Override
			public void onComplete() {
				// Get access to the URI for the bitmap
				Uri bmpUri = getLocalBitmapUri(iv);
				if (bmpUri != null) {
					// Attach share event to the menu item provider
					miShareAction.setShareIntent(setupShareIntent(bmpUri));
				} else {
					// ...sharing failed, handle error
					Toast.makeText(ImageDisplayActivity.this,
							"Sharing image failed. returning",
							Toast.LENGTH_SHORT).show();
					finish();
				}
			}

		});

		iv.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// Get access to the URI for the bitmap
				Uri bmpUri = getLocalBitmapUri(iv);
				if (bmpUri != null) {
					// Launch sharing dialog for image
					startActivity(Intent.createChooser(
							setupShareIntent(bmpUri), "Share Image"));
				} else {
					// ...sharing failed, handle error
					Toast.makeText(ImageDisplayActivity.this,
							"Sharing image failed. returning",
							Toast.LENGTH_SHORT).show();
					finish();
				}
				return true;
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu resource file.
		getMenuInflater().inflate(R.menu.menu_detail_activity, menu);
		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.miShare);
		// Fetch reference to the share action provider
		miShareAction = (ShareActionProvider) item.getActionProvider();
		// Return true to display menu
		return true;
	}

	public Intent setupShareIntent(Uri bmpUri) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
		shareIntent.putExtra(Intent.EXTRA_EMAIL, bmpUri); //doesnt work
		shareIntent.setType("image/*");
		return shareIntent;
	}

	// Returns the URI path to the Bitmap displayed in specified ImageView
	private Uri getLocalBitmapUri(ImageView imageView) {
		// Extract Bitmap from ImageView drawable
		Drawable drawable = imageView.getDrawable();
		Bitmap bmp = null;
		if (drawable instanceof BitmapDrawable) {
			bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		} else {
			return null;
		}
		// Store image to default external storage directory
		Uri bmpUri = null;
		try {
			File file = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					"share_image_" + System.currentTimeMillis() + ".png");
			file.getParentFile().mkdirs();
			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.close();
			bmpUri = Uri.fromFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmpUri;
	}
}
