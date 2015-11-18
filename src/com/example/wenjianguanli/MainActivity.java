package com.example.wenjianguanli;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	private ListView mListView;
	private Button mBackBtn;
	private ArrayList<File> data, mParentPath;
	private ArrayList<String> path;
	private File rootFile;
	private LinearLayout mLinearLayout;
	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.file_dec_listview);

		mBackBtn = (Button) findViewById(R.id.file_nav_txt);
		mBackBtn.setOnClickListener(this);
		mLinearLayout = (LinearLayout) findViewById(R.id.file_liner_layout);

		
		mListView.setOnItemClickListener(this);
		mParentPath = new ArrayList<File>();
		rootFile = Environment.getExternalStorageDirectory();
		mParentPath.add(rootFile);
		showFile(rootFile);
	}

	public void showFile(File file) {
		data = new ArrayList<File>();
		path = new ArrayList<String>();
		
		File[] listFile = file.listFiles();

		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				if (listFile[i].isDirectory()) {

					path.add(listFile[i].getPath());// 保存路径
					
					data.add(listFile[i]);// 保存文件
				} else {
					data.add(listFile[i]);
				}

			}
		}

		MyFileAdapter adapter = new MyFileAdapter(this);
		adapter.setList(data);
		mListView.setAdapter(adapter);
	}

	/*
	 * 打开文件
	 */
	public void openFile(File file) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		if (file.getName().endsWith("png") || file.getName().endsWith("jpg")) {
			intent.setDataAndType(Uri.fromFile(file), "image/*");
		} else if (file.getName().endsWith("mp3")) {
			intent.setDataAndType(Uri.fromFile(file), "audio/mp3");
		}

		startActivity(intent);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		File fN = data.get(arg2);

		if (path.size() > arg2) {
			File f = new File(path.get(arg2));
			if (f.isDirectory()) {
				mParentPath.add(f.getParentFile());
				Log.e("tag", "-----父类文件路径"+f.getParentFile());
				createView(fN);

				showFile(f);// 如果是文件夹浏览后重新设置适配器显示数据
			} else {
				openFile(f);
			}
		} else {
			openFile(fN);
		}

	}

	@SuppressLint("ResourceAsColor")
	protected View createView(final File fileName) {// 动态添加组件
		final Button btn = new Button(this);// 动态创建按钮
		btn.setId(index++);

//		btn.setBackground(getResources()
//				.getDrawable(R.drawable.aaaaaaaa));
		btn.setBackgroundResource(R.drawable.aaaaaaaa);
		btn.setGravity(Gravity.CENTER_VERTICAL);
		
		btn.setText(fileName.getName());
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("tag", "btn.getId-----"+btn.getId()+"----"+mParentPath.get(btn.getId()+2));
					showFile(mParentPath.get(btn.getId()+2));
					for (int i = 0; i < index-btn.getId()-1; i++) {
					removeView();
					}

			}
		});

		mLinearLayout.addView(btn);
		return btn;
	}

	private void removeView() {// 动态删除组件（按钮）
		int count = mLinearLayout.getChildCount();
		if (count - 1 > 0) {
			mLinearLayout.removeViewAt(count - 1);
		}
	}

	@Override
	public void onClick(View v) {

		showFile(rootFile);
		for (int i = 0; i < index; i++) {
			removeView();
		}
		index = 0;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		Log.d("tag", "----------" + index + "-----" + mParentPath.size() + "------"
				+ mParentPath.get(index));
		if (index > 0) {
			showFile(mParentPath.get(index--));
			removeView();
		} else {
			finish();
		}

		return true;
	}
}
