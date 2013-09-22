package org.kdm.gogomtnaejang.db;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.kdm.gogomtnaejang.StartLoadingActivity;
import org.kdm.gogomtnaejang.node.Path;
import org.kdm.gogomtnaejang.node.SimpleNode;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;

public class ManageSQLite {
	public static final String TABLE_LIST[] = new String[1];
	private final int jeongeup_naejang_path = 0;

	private static ManageSQLite mManageSQLLite = null;
	public static final String dbName = "AllData.db";
	// private final String tableName = "NodeInfo";
	private final int dbVersion = 1;

	private OpenHelper opener;
	private SQLiteDatabase db;

	private Context mContext;

	private ManageSQLite(Context context) {
		setTableList();
		setContext(context);
		try {
			this.opener = new OpenHelper(mContext, dbName, null, dbVersion);
			db = opener.getWritableDatabase();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// dropAllTable();
		// saveDatabase();
		initTable();
	}

	public static ManageSQLite getInst(Context context) {
		if (mManageSQLLite == null)
			mManageSQLLite = new ManageSQLite(context);

		mManageSQLLite.setContext(context);
		return mManageSQLLite;
	}

	private void setContext(Context context) {
		this.mContext = context;
	}

	private void setTableList() {
		ManageSQLite.TABLE_LIST[jeongeup_naejang_path] = "jeongeup_naejang_path";
	}

	public void initTable() {
		for (int i = 0; i < ManageSQLite.TABLE_LIST.length; i++) {
			openTable(ManageSQLite.TABLE_LIST[i]);
		}
	}

	public void openTable(String tableName) {
		if (tableName.equals(ManageSQLite.TABLE_LIST[0])) {
			String createSql = "create table if not exists " + tableName + " ("
					+ "_index integer PRIMARY KEY autoincrement, "
					+ "id integer, " + "lat real, " + "lng real);";
			db.execSQL(createSql);
		}
	}

	public void dropAllTable() {
		for (int i = 0; i < ManageSQLite.TABLE_LIST.length; i++) {
			String dropSql = "drop table '" + ManageSQLite.TABLE_LIST[i] + "';";
			db.execSQL(dropSql);
		}
	}

	public void saveDatabase() {
		try {
			FileInputStream fis = new FileInputStream(
					StartLoadingActivity.BASE_DATABASE_DIR);
			FileOutputStream fos = new FileOutputStream(
					StartLoadingActivity.BASE_SDCARD_DIR + "/AllData.db");

			Log.e("err", StartLoadingActivity.BASE_SDCARD_DIR + "/AllData.db");
			int data = 0;
			while ((data = fis.read()) != -1) {
				fos.write(data);
			}
			fis.close();
			fos.close();
			Log.e("err", "파일 쓰기 끝!");
			while (true) {

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void setAllPath(SparseArray<Path> pathList) {
		for (int id = 1; id <= 58; id++) {
			Log.e("err", "inputToSQL" + id + "");
			try {
				ArrayList<SimpleNode> path = pathList.get(id).getPath();
				for (int i = 0; i < path.size(); i++) {
					String sql = "insert into "
							+ TABLE_LIST[jeongeup_naejang_path]
							+ " values(NULL, '" + id + "', '" + path.get(i).lat
							+ "', '" + path.get(i).lng + "');";
					db.execSQL(sql);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public SparseArray<Path> getAllPath(Handler handler) {
		String sql = "select * from " + TABLE_LIST[jeongeup_naejang_path] + ";";
		Cursor results = db.rawQuery(sql, null);
		results.moveToFirst();
		SparseArray<Path> pathList = null;
		pathList = new SparseArray<Path>();

		while (results.moveToNext()) {
			int id;
			double lat, lng;
			id = results.getInt(1);
			lat = results.getDouble(2);
			lng = results.getDouble(3);

			if (pathList.get(id) == null) {
				Message msg = handler.obtainMessage();
				pathList.put(id, new Path(id));

				Bundle bundle = new Bundle();

				bundle.putInt("total", id+40);
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
			pathList.get(id).putSimpleNode(lat, lng);
		}

		if (pathList.size() == 0)
			return null;
		else
			return pathList;
	}
}
