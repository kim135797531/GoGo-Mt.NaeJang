package org.kdm.gogomtnaejang.information;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.climbmt.Track;
import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogomtnaejang.node.InfoNode;
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

public class InfoListAdapter extends BaseAdapter {

	private Context mContext = null;
	private ArrayList<InfoNode> infoList = null;
	private ImageLoader mImageLoader = null;

	public InfoListAdapter(Context context, ArrayList<InfoNode> infoList) {
		this.mContext = context;
		this.infoList = infoList;
		this.mImageLoader = ManageVolley.getInst(context).getImageLoader();
		ManageVolley.getInst(context).getRequestQueue().cancelAll(mContext);
	}

	@Override
	public int getCount() {
		return infoList.size();
	}

	@Override
	public Object getItem(int position) {
		return infoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return infoList.get(position).id;
	}

	public void setItem(ArrayList<InfoNode> infoList) {
		this.infoList = null;
		this.infoList = infoList;
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

		InfoNode infoNode = infoList.get(position);
		setImage(infoImage, infoNode);
		infoName.setText(infoNode.name);
		infoDescription.setText(infoNode.detailDescription);
		return ret;
	}

	private void setImage(FadeInNetworkImageView infoImage, InfoNode infoNode) {
		if (infoNode.thumbImageURL.equalsIgnoreCase("NO IMAGE")) {
			infoImage.setImageUrl(ManageNetwork.SERVER_SRC
					+ "/works/jeongueop/image/app_icon.png", mImageLoader);
		} else {
			infoImage.setImageUrl(ManageNetwork.SERVER_SRC
					+ "/works/jeongueop/image/info/" + infoNode.thumbImageURL
					+ ".png", mImageLoader);
		}
	}
}
