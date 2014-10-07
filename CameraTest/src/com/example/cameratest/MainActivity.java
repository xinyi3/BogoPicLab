package com.example.cameratest;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

	Uri imageFileUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
		OnClickListener listener = new OnClickListener() {
			public void onClick(View v) {
				takeAPhoto();
			}
		};
		button.setOnClickListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	public void takeAPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        
        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }
        
        String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + "jpg";
        File imageFile = new File(imageFilePath);
        imageFileUri = Uri.fromFile(imageFile);
        
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	      if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	          TextView tv = (TextView) findViewById(R.id.status);
          if (resultCode == RESULT_OK) {
	             tv.setText("Random message");
	             ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
	             button.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
	         } else if (resultCode == RESULT_CANCELED) {
	             tv.setText("Photo canceled");
	         } else {
	             tv.setText("Not sure what happened!" + resultCode);
	         }
	      }
	}

}
