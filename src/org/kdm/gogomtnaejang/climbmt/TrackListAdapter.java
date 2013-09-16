package org.kdm.gogomtnaejang.climbmt;

import java.util.ArrayList;

import org.kdm.gogonaejangmt.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TrackListAdapter extends BaseAdapter{
	
	private Context mContext = null;
	private ArrayList<Track> trackList = null;
	
	public TrackListAdapter(Context context, ArrayList<Track> trackList){
		this.mContext = context;
		this.trackList = trackList;
	}
	
	@Override
	public int getCount(){
		return trackList.size();
	}
	
	@Override
	public Object getItem(int position){
		return trackList.get(position);
	}
	
	@Override
	public long getItemId(int position){
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		RelativeLayout ret = null;
		if(convertView != null){
			ret = (RelativeLayout) convertView;			
		}
		else{
			LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ret = (RelativeLayout)layoutInflater.inflate(R.layout.view_list_item, null);
			
			ImageView infoImage = (ImageView) ret.findViewById(R.id.infoImage);
			TextView infoName = (TextView) ret.findViewById(R.id.infoName);
			TextView infoDescription = (TextView) ret.findViewById(R.id.infoDescription);
			
			Track track = trackList.get(position);
			//infoImage.setImageResource(track.imageID);
			infoImage.setImageBitmap(decodeBitmapForThumb(track.thumbImageID));
			infoName.setText(track.title);
			infoDescription.setText(track.description);
		}
		
		return ret;
	}
	
	private Bitmap decodeBitmapForThumb(String thumbImageID){
		int thumbImageResourceID = mContext.getResources().getIdentifier(thumbImageID, null, null);
		if(thumbImageResourceID == 0)
			return ((BitmapDrawable) mContext.getResources().getDrawable(R.drawable.app_icon)).getBitmap();
		BitmapDrawable originalPicture = (BitmapDrawable) mContext.getResources().getDrawable(thumbImageResourceID);
		Bitmap bitmap = originalPicture.getBitmap();
		
		//BitmapFactory.Options options = new BitmapFactory.Options();
		//options.inSampleSize = 8;
		//Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), imageID, options);
		return bitmap;
	}
}
