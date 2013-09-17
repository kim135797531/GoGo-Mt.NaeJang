package org.kdm.gogomtnaejang.community;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.network.GetOneImageFunc;
import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogonaejangmt.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BoardListAdapter extends BaseAdapter {

	private Context mContext = null;
	private ArrayList<BoardDocument> documentList = null;

	public BoardListAdapter(Context context, ArrayList<BoardDocument> documentList) {
		this.mContext = context;
		this.documentList = documentList;
	}

	@Override
	public int getCount() {
		return documentList.size();
	}

	@Override
	public Object getItem(int position) {
		return documentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setItem(ArrayList<BoardDocument> documentList) {
		this.documentList = documentList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout ret = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ret = (RelativeLayout) layoutInflater.inflate(
					R.layout.view_list_item, null);
		} else {
			ret = (RelativeLayout) convertView;
		}

		ImageView infoImage = (ImageView) ret.findViewById(R.id.infoImage);
		TextView infoName = (TextView) ret.findViewById(R.id.infoName);
		TextView infoDescription = (TextView) ret
				.findViewById(R.id.infoDescription);

		BoardDocument document = documentList.get(position);
		setImage(infoImage, document);
		infoName.setText(document.title);
		infoDescription.setText(document.IMEI);

		return ret;
		
//		RelativeLayout ret = null;
//		if (convertView != null) {
//			ret = (RelativeLayout) convertView;
//		} else {
//			LayoutInflater layoutInflater = (LayoutInflater) mContext
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			ret = (RelativeLayout) layoutInflater.inflate(
//					R.layout.view_list_item, null);
//
//			ImageView infoImage = (ImageView) ret.findViewById(R.id.infoImage);
//			TextView infoName = (TextView) ret.findViewById(R.id.infoName);
//			TextView infoDescription = (TextView) ret
//					.findViewById(R.id.infoDescription);
//
//			BoardDocument document = documentList.get(position);
//			setImage(infoImage, document);
//			infoName.setText(document.title);
//			infoDescription.setText(document.IMEI);
//		}
//
//		return ret;
	}

	private void setImage(ImageView infoImage, BoardDocument document) {
		if (document.thumbImageURL.equalsIgnoreCase("No Image")) {
			infoImage.setImageResource(R.drawable.app_icon);
		} else {
			try {
				Bitmap bitmap = ManageNetwork.getInst().downloadOneImageFunc();
				infoImage.setImageBitmap(bitmap);
			} catch (Exception ex) {
				infoImage.setImageResource(R.drawable.app_icon);
			}
		}
	}
}
