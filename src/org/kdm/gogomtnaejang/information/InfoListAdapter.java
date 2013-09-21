package org.kdm.gogomtnaejang.information;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.node.InfoNode;
import org.kdm.gogonaejangmt.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InfoListAdapter extends BaseAdapter {

	private Context mContext = null;
	private ArrayList<InfoNode> infoList = null;

	public InfoListAdapter(Context context, ArrayList<InfoNode> infoList) {
		this.mContext = context;
		this.infoList = infoList;
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
		this.infoList = infoList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout ret = null;
		if (convertView != null) {
			ret = (RelativeLayout) convertView;
		} else {
			LayoutInflater layoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ret = (RelativeLayout) layoutInflater.inflate(
					R.layout.view_list_item, null);

			ImageView infoImage = (ImageView) ret.findViewById(R.id.infoImage);
			TextView infoName = (TextView) ret.findViewById(R.id.infoName);
			TextView infoDescription = (TextView) ret
					.findViewById(R.id.infoDescription);

			InfoNode infoNode = infoList.get(position);
			infoImage
					.setImageResource(getThumbBitmapResource(infoNode.thumbImageURL));
			infoName.setText(infoNode.name);
			infoDescription.setText(infoNode.detailDescription);
		}
		return ret;
	}

	private int getThumbBitmapResource(String thumbImageID) {
		if(thumbImageID.equalsIgnoreCase("NO IMAGE"))
			return R.drawable.app_icon;
		
		return mContext.getResources().getIdentifier(thumbImageID, "drawable",	"org.kdm.gogonaejangmt");
	}
}
