package org.kdm.gogomtnaejang.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.kdm.gogomtnaejang.StartLoadingActivity;

import android.os.AsyncTask;
import android.util.Log;

public class GetAllPathDBFileFunc extends AsyncTask<Void, Void, Void>{

	@Override
	protected Void doInBackground(Void... params) {
		// 웹페이지에서 다운받은 db파일을 해당 패키지 db로 저장한다.
				final String DIR = StartLoadingActivity.BASE_DATABASE_DIR;
				final String PATH = DIR;
				File fDir = new File(DIR);
				File f = new File(PATH);
		 
				FileOutputStream fos = null;
				BufferedOutputStream bos = null;
				InputStream is = null;
				BufferedInputStream bis = null;
		 
				try {
					URL url = new URL("http://kim135797531.cafe24.com/works/jeongueop/AllData.db");
					HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		 
					if(!fDir.exists()){
						fDir.mkdir();
					}
					if (f.exists()) {
						f.delete();
						f.createNewFile();
					}
					is = httpConn.getInputStream();
					bis = new BufferedInputStream(is);
		 
							
					fos = new FileOutputStream(f);
					bos = new BufferedOutputStream(fos);
		 
					int read = -1;
					int len = httpConn.getContentLength();
					byte[] buffer = new byte[len];
		 
					while ((read = bis.read(buffer, 0, 1024)) != -1) {
						bos.write(buffer, 0, read);
					}
					bos.flush();		
					Log.e("err", "write sucess");
				} catch (IOException e) {
					Log.e("err", "file Write error");
				} finally {
					try {
						fos.close();
						bos.close();
						is.close();
						bis.close();
					} catch (IOException e) {
		 
					}
				}
			return null;
	}

}
