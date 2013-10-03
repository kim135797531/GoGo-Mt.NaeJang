package org.kdm.gogomtnaejang.climbmt;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.community.BoardDocument;
import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogomtnaejang.volley.FadeInNetworkImageView;
import org.kdm.gogomtnaejang.volley.ManageVolley;
import org.kdm.gogonaejangmt.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

public class TrackListAdapter extends BaseAdapter {

	private Context mContext = null;
	private ArrayList<Track> trackList = null;
	private ImageLoader mImageLoader = null;

	public TrackListAdapter(Context context, ArrayList<Track> trackList) {
		this.mContext = context;
		this.trackList = trackList;
		this.mImageLoader = ManageVolley.getInst(context).getImageLoader();
		ManageVolley.getInst(context).getRequestQueue().cancelAll(null);
	}

	@Override
	public int getCount() {
		return trackList.size();
	}

	@Override
	public Object getItem(int position) {
		return trackList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
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

		FadeInNetworkImageView infoImage = (FadeInNetworkImageView) ret
				.findViewById(R.id.infoImage);
		TextView infoName = (TextView) ret.findViewById(R.id.infoName);
		TextView infoDescription = (TextView) ret
				.findViewById(R.id.infoDescription);

		Track track = trackList.get(position);
		setImage(infoImage, track);
		infoName.setText(track.title);
		infoDescription.setText(track.description);
		return ret;
	}

	private void setImage(FadeInNetworkImageView infoImage, Track track) {
		if (track.thumbImageID.equalsIgnoreCase("NO IMAGE")) {
			infoImage.setImageUrl(ManageNetwork.SERVER_SRC
					+ "/works/jeongueop/image/app_icon.png", mImageLoader);
		} else {
			infoImage.setImageUrl(ManageNetwork.SERVER_SRC
					+ "/works/jeongueop/image/track/" + track.thumbImageID
					+ ".png", mImageLoader);
		}
	}
}
