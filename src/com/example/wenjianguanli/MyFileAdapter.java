package com.example.wenjianguanli;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PBEParameterSpec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyFileAdapter extends BaseAdapter {

	ArrayList<File> list = new ArrayList<File>();
	private Context context;
	private LayoutInflater layoutInflater;

	public MyFileAdapter(Context context) {
		layoutInflater = LayoutInflater.from(context);
	}

	public void setList(ArrayList<File> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.file_dec_list_tiem,
					null);
			viewHolder = new ViewHolder();
			viewHolder.docFile = (TextView) convertView.findViewById(R.id.file_list_dec_txt);
			viewHolder.imageFile = (ImageView) convertView.findViewById(R.id.file_list_one_img);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		File file = list.get(position);
		
		viewHolder.docFile.setText(file.getName());
		if (file.isDirectory()) {
			viewHolder.imageFile.setImageResource(R.drawable.filesystem_icon_folder);
		}else if(file.getName().endsWith("png")||file.getName().endsWith("jpg")){
			viewHolder.imageFile.setImageResource(R.drawable.filesystem_grid_icon_photo);
		}else {
			viewHolder.imageFile.setImageResource(R.drawable.filesystem_icon_music);
		}
		
		
		

		return convertView;
	}

	public class ViewHolder {
		TextView docFile;
		ImageView imageFile;
	}

}
