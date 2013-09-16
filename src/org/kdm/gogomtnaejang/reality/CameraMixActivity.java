package org.kdm.gogomtnaejang.reality;

import java.util.Iterator;
import java.util.List;

import org.mixare.lib.gui.PaintScreen;
import org.mixare.lib.render.Matrix;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("FloatMath")
public class CameraMixActivity extends Activity implements SensorEventListener, OnTouchListener{
	private CameraSurface camScreen;
	private AugmentedView augScreen;
	
	private boolean isFirstRun = true;
	private boolean isInited;
	private static PaintScreen dWindow;
	private static CameraDataView dataView;
	private boolean fError;
	
	private static CameraMixActivity mActivity;
	private MixViewDataHolder mixViewData;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		try{
			final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			getMixViewData().setmWakeLock(pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "MyFakeTrip"));
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			maintainCamera();
			maintainAugmentR();
			
			if(!isInited){
				setdWindow(new PaintScreen());
				setDataView(new CameraDataView(this, getMixViewData().getMixContext()));
				isInited = true;
			}
			
		}catch(Exception e){
			
		}
	}
	
	public static CameraMixActivity getActivity(){
		return mActivity;
	}
	
	public boolean getIsFirstRun(){
		return isFirstRun;
	}
	
	public void setIsFirstRun(boolean isFirstRun){
		this.isFirstRun = isFirstRun; 
	}
	@Override
	protected void onPause() {
		super.onPause();

		try {
			this.getMixViewData().getmWakeLock().release();

			try {
				getMixViewData().getSensorMgr().unregisterListener(this,getMixViewData().getSensorGrav());
				getMixViewData().getSensorMgr().unregisterListener(this,getMixViewData().getSensorMag());
				getMixViewData().setSensorMgr(null);
				
				getMixViewData().getMixContext().getLocationFinder().switchOff();

				if (getDataView() != null) {
					getDataView().cancelRefreshTimer();
				}
			} catch (Exception ignore) {
			}

			if (fError) {
				finish();
			}
		} catch (Exception ex) {
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		try{
			this.getMixViewData().getmWakeLock().acquire();
			getMixViewData().getMixContext().doResume(this);
			
			repaint();
			//getDataView().doStart();
			//getDataView().clearEvents();
			
			float angleX, angleY;
			
			int marker_orientation = -90;
			
			int rotation = CameraViewCompatibility.getRotation(this);
			angleX = (float) Math.toRadians(marker_orientation);
			getMixViewData().getM1().set(1f, 0f, 0f, 0f,
					(float) FloatMath.cos(angleX),
					(float) -FloatMath.sin(angleX), 0f,
					(float) FloatMath.sin(angleX),
					(float) FloatMath.cos(angleX));
			angleX = (float) Math.toRadians(marker_orientation);
			angleY = (float) Math.toRadians(marker_orientation);
			
			if (rotation == 1) {
				getMixViewData().getM2().set(1f, 0f, 0f, 0f,
						(float) FloatMath.cos(angleX),
						(float) -FloatMath.sin(angleX), 0f,
						(float) FloatMath.sin(angleX),
						(float) FloatMath.cos(angleX));
				getMixViewData().getM3().set((float) FloatMath.cos(angleY), 0f,
						(float) FloatMath.sin(angleY), 0f, 1f, 0f,
						(float) -FloatMath.sin(angleY), 0f,
						(float) FloatMath.cos(angleY));
			} else {
				getMixViewData().getM2().set((float) FloatMath.cos(angleX), 0f,
						(float) FloatMath.sin(angleX), 0f, 1f, 0f,
						(float) -FloatMath.sin(angleX), 0f,
						(float) FloatMath.cos(angleX));
				getMixViewData().getM3().set(1f, 0f, 0f, 0f,
						(float) FloatMath.cos(angleY),
						(float) -FloatMath.sin(angleY), 0f,
						(float) FloatMath.sin(angleY),
						(float) FloatMath.cos(angleY));
			}
			
			
			getMixViewData().getM4().toIdentity();

			for (int i = 0; i < getMixViewData().getHistR().length; i++) {
				getMixViewData().getHistR()[i] = new Matrix();
			}

			getMixViewData().setSensorMgr((SensorManager) getSystemService(SENSOR_SERVICE));
			getMixViewData().setSensors(getMixViewData().getSensorMgr().getSensorList(Sensor.TYPE_ACCELEROMETER));
			if (getMixViewData().getSensors().size() > 0) {
				getMixViewData().setSensorGrav(getMixViewData().getSensors().get(0));
			}
			getMixViewData().setSensors(getMixViewData().getSensorMgr().getSensorList(Sensor.TYPE_MAGNETIC_FIELD));
			if (getMixViewData().getSensors().size() > 0) {
				getMixViewData().setSensorMag(getMixViewData().getSensors().get(0));
			}

			getMixViewData().getSensorMgr().registerListener(this,getMixViewData().getSensorGrav(), SensorManager.SENSOR_DELAY_GAME);
			getMixViewData().getSensorMgr().registerListener(this,getMixViewData().getSensorMag(), SensorManager.SENSOR_DELAY_GAME);

			try {
				GeomagneticField gmf = getMixViewData().getMixContext().getLocationFinder().getGeomagneticField(); 
				angleY = (float) Math.toRadians(-gmf.getDeclination());
				getMixViewData().getM4().set((float) FloatMath.cos(angleY), 0f,
						(float) FloatMath.sin(angleY), 0f, 1f, 0f,
						(float) -FloatMath.sin(angleY), 0f,
						(float) FloatMath.cos(angleY));
			} catch (Exception ex) {
				Log.d("mixare", "GPS Initialize Error", ex);
			}

			getMixViewData().getMixContext().getLocationFinder().switchOn();
		}catch(Exception e){};

	}
	
	protected void onRestart (){
		super.onRestart();
		maintainCamera();
		maintainAugmentR();
	}

	public void repaint(){
		//getDataView().clearEvents();
		//setDataView(null);
		//setDataView(new CameraDataView(this, mixViewData.getMixContext()));
		if(getDataView().mHandler != null){
			Message msg = new Message();
			msg.what = CameraDataView.DOWNLOAD_FINISH;		
			getDataView().mHandler.sendMessage(msg);
		}
		setdWindow(new PaintScreen());
		mActivity = this;		
	}
	
	private void maintainCamera(){

		if(camScreen == null){
			camScreen = new CameraSurface(this);
		}
		setContentView(camScreen);
	}
	
	private void maintainAugmentR() {
		if (augScreen == null ){
		augScreen = new AugmentedView(this);
		}
		addContentView(augScreen, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					
	}
	
	
	static CameraDataView getDataView(){
		return dataView;
	}
	
	static void setDataView(CameraDataView dataView){
		CameraMixActivity.dataView = dataView;
	}
	
	static void setdWindow(PaintScreen dWindow){
		CameraMixActivity.dWindow = dWindow;
	}

	public MixViewDataHolder getMixViewData(){
		if(mixViewData == null){
			mixViewData = new MixViewDataHolder(new CameraMixContext(this));
		}
		return mixViewData;
	}

	public static PaintScreen getdWindow() {
		return dWindow;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		try {
			float xPress = me.getX();
			float yPress = me.getY();
			if (me.getAction() == MotionEvent.ACTION_UP) {
				getDataView().clickEvent(xPress, yPress);
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return super.onTouchEvent(me);
		}
	}	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD
				&& accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE
				&& getMixViewData().getCompassErrorDisplayed() == 0) {
			for (int i = 0; i < 2; i++) {
				Toast.makeText(getMixViewData().getMixContext(),
						"Compass data unreliable. Please recalibrate compass.",
						Toast.LENGTH_LONG).show();
			}
			getMixViewData().setCompassErrorDisplayed(getMixViewData()
					.getCompassErrorDisplayed() + 1);
		}
	}

	@Override
	public void onSensorChanged(SensorEvent evt) {
		try {
			if (evt.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				getMixViewData().getGrav()[0] = evt.values[0];
				getMixViewData().getGrav()[1] = evt.values[1];
				getMixViewData().getGrav()[2] = evt.values[2];
				augScreen.postInvalidate();
			} else if (evt.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				getMixViewData().getMag()[0] = evt.values[0];
				getMixViewData().getMag()[1] = evt.values[1];
				getMixViewData().getMag()[2] = evt.values[2];
				augScreen.postInvalidate();
			}

			SensorManager.getRotationMatrix(getMixViewData().getRTmp(),
					getMixViewData().getI(), getMixViewData().getGrav(),
					getMixViewData().getMag());

			int rotation = CameraViewCompatibility.getRotation(this);

			if (rotation == 1) {
				SensorManager.remapCoordinateSystem(getMixViewData().getRTmp(),
						SensorManager.AXIS_X, SensorManager.AXIS_MINUS_Z,
						getMixViewData().getRot());
			} else {
				SensorManager.remapCoordinateSystem(getMixViewData().getRTmp(),
						SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_Z,
						getMixViewData().getRot());
			}
			getMixViewData().getTempR().set(getMixViewData().getRot()[0],
					getMixViewData().getRot()[1], getMixViewData().getRot()[2],
					getMixViewData().getRot()[3], getMixViewData().getRot()[4],
					getMixViewData().getRot()[5], getMixViewData().getRot()[6],
					getMixViewData().getRot()[7], getMixViewData().getRot()[8]);

			getMixViewData().getFinalR().toIdentity();
			getMixViewData().getFinalR().prod(getMixViewData().getM4());
			getMixViewData().getFinalR().prod(getMixViewData().getM1());
			getMixViewData().getFinalR().prod(getMixViewData().getTempR());
			getMixViewData().getFinalR().prod(getMixViewData().getM3());
			getMixViewData().getFinalR().prod(getMixViewData().getM2());
			getMixViewData().getFinalR().invert();

			getMixViewData().getHistR()[getMixViewData().getrHistIdx()].set(getMixViewData()
					.getFinalR());
			getMixViewData().setrHistIdx(getMixViewData().getrHistIdx() + 1);
			if (getMixViewData().getrHistIdx() >= getMixViewData().getHistR().length)
				getMixViewData().setrHistIdx(0);

			getMixViewData().getSmoothR().set(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
			for (int i = 0; i < getMixViewData().getHistR().length; i++) {
				getMixViewData().getSmoothR().add(getMixViewData().getHistR()[i]);
			}
			getMixViewData().getSmoothR().mult(
					1 / (float) getMixViewData().getHistR().length);

			getMixViewData().getMixContext().updateSmoothRotation(getMixViewData().getSmoothR());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
}

class CameraSurface extends SurfaceView implements SurfaceHolder.Callback{
	CameraMixActivity app;
	SurfaceHolder holder;
	Camera camera;
	
	public CameraSurface(Context context) {
		super(context);
		
		try{
			app = (CameraMixActivity) context;
			
			holder = getHolder();
			holder.addCallback(this);
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}catch(Exception e){
			
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		try {
			Camera.Parameters parameters = camera.getParameters();
			try {
				List<Camera.Size> supportedSizes = null;
				supportedSizes = CameraViewCompatibility.getSupportedPreviewSizes(parameters);

				//화면 비율
				float ff = (float) width / height;

				// holder for the best form factor and size
				float bff = 0;
				int bestw = 0;
				int besth = 0;
				Iterator<Camera.Size> itr = supportedSizes.iterator();

				while (itr.hasNext()) {
					Camera.Size element = itr.next();
					float cff = (float) element.width / element.height;
					if ((ff - cff <= ff - bff) && (element.width <= width)
							&& (element.width >= bestw)) {
						bff = cff;
						bestw = element.width;
						besth = element.height;
					}
				}
				Log.d("Mixare", "Chosen camera element: w:" + bestw + " h:"	+ besth + " aspect ratio:" + bff);

				if ((bestw == 0) || (besth == 0)) {
					Log.d("Mixare", "Using default camera parameters!");
					bestw = 480;
					besth = 320;
				}
				parameters.setPreviewSize(bestw, besth);
			} catch (Exception ex) {
				parameters.setPreviewSize(480, 320);
			}

			camera.setParameters(parameters);
			camera.startPreview();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			if (camera != null) {
				try {
					camera.stopPreview();
				} catch (Exception ignore) {
				}
				try {
					camera.release();
				} catch (Exception ignore) {
				}
				camera = null;
			}

			camera = Camera.open();
			camera.setPreviewDisplay(holder);
		} catch (Exception ex) {
			try {
				if (camera != null) {
					try {
						camera.stopPreview();
					} catch (Exception ignore) {
					}
					try {
						camera.release();
					} catch (Exception ignore) {
					}
					camera = null;
				}
			} catch (Exception ignore) {

			}
		}		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		try {
			if (camera != null) {
				try {
					camera.stopPreview();
				} catch (Exception ignore) {
				}
				try {
					camera.release();
				} catch (Exception ignore) {
				}
				camera = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}

class AugmentedView extends View {
	CameraMixActivity app;
	int xSearch = 200;
	int ySearch = 10;
	int searchObjWidth = 0;
	int searchObjHeight = 0;

	Paint zoomPaint = new Paint();

	public AugmentedView(Context context) {
		super(context);

		try {
			app = (CameraMixActivity) context;
		} catch (Exception ex) {
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		try {
			CameraMixActivity.getdWindow().setWidth(canvas.getWidth());
			CameraMixActivity.getdWindow().setHeight(canvas.getHeight());
			CameraMixActivity.getdWindow().setCanvas(canvas);
			if(!CameraMixActivity.getDataView().getIsInited()){
				CameraMixActivity.getDataView().setIsInited(true);
				CameraMixActivity.getDataView().setActivity(CameraMixActivity.getActivity());
				CameraMixActivity.getDataView().init(CameraMixActivity.getdWindow().getWidth(),	CameraMixActivity.getdWindow().getHeight());
			}
			CameraMixActivity.getDataView().draw(CameraMixActivity.getdWindow());			
		} catch (Exception ex) {
		}
	}
}

class MixViewDataHolder{
	private final CameraMixContext mixContext;
	private float[] RTmp;
	private float[] Rot;
	private float[] I;
	private float[] grav;
	private float[] mag;
	private SensorManager sensorMgr;
	private List<Sensor> sensors;
	private Sensor sensorGrav;
	private Sensor sensorMag;
	private int rHistIdx;
	private Matrix tempR;
	private Matrix finalR;
	private Matrix smoothR;
	private Matrix[] histR;
	private Matrix m1;
	private Matrix m2;
	private Matrix m3;
	private Matrix m4;
	private SeekBar myZoomBar;
	private WakeLock mWakeLock;
	private int compassErrorDisplayed;
	private String zoomLevel;
	private int zoomProgress;
	private TextView searchNotificationTxt;

	public MixViewDataHolder(CameraMixContext mixContext) {
		this.mixContext=mixContext;
		this.RTmp = new float[9];
		this.Rot = new float[9];
		this.I = new float[9];
		this.grav = new float[3];
		this.mag = new float[3];
		this.rHistIdx = 0;
		this.tempR = new Matrix();
		this.finalR = new Matrix();
		this.smoothR = new Matrix();
		this.histR = new Matrix[60];
		this.m1 = new Matrix();
		this.m2 = new Matrix();
		this.m3 = new Matrix();
		this.m4 = new Matrix();
		this.compassErrorDisplayed = 0;
	}

	/* ******* Getter and Setters ********** */
	public CameraMixContext getMixContext() {
		return mixContext;
	}

	public float[] getRTmp() {
		return RTmp;
	}

	public void setRTmp(float[] rTmp) {
		RTmp = rTmp;
	}

	public float[] getRot() {
		return Rot;
	}

	public void setRot(float[] rot) {
		Rot = rot;
	}

	public float[] getI() {
		return I;
	}

	public void setI(float[] i) {
		I = i;
	}

	public float[] getGrav() {
		return grav;
	}

	public void setGrav(float[] grav) {
		this.grav = grav;
	}

	public float[] getMag() {
		return mag;
	}

	public void setMag(float[] mag) {
		this.mag = mag;
	}

	public SensorManager getSensorMgr() {
		return sensorMgr;
	}

	public void setSensorMgr(SensorManager sensorMgr) {
		this.sensorMgr = sensorMgr;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public Sensor getSensorGrav() {
		return sensorGrav;
	}

	public void setSensorGrav(Sensor sensorGrav) {
		this.sensorGrav = sensorGrav;
	}

	public Sensor getSensorMag() {
		return sensorMag;
	}

	public void setSensorMag(Sensor sensorMag) {
		this.sensorMag = sensorMag;
	}

	public int getrHistIdx() {
		return rHistIdx;
	}

	public void setrHistIdx(int rHistIdx) {
		this.rHistIdx = rHistIdx;
	}

	public Matrix getTempR() {
		return tempR;
	}

	public void setTempR(Matrix tempR) {
		this.tempR = tempR;
	}

	public Matrix getFinalR() {
		return finalR;
	}

	public void setFinalR(Matrix finalR) {
		this.finalR = finalR;
	}

	public Matrix getSmoothR() {
		return smoothR;
	}

	public void setSmoothR(Matrix smoothR) {
		this.smoothR = smoothR;
	}

	public Matrix[] getHistR() {
		return histR;
	}

	public void setHistR(Matrix[] histR) {
		this.histR = histR;
	}

	public Matrix getM1() {
		return m1;
	}

	public void setM1(Matrix m1) {
		this.m1 = m1;
	}

	public Matrix getM2() {
		return m2;
	}

	public void setM2(Matrix m2) {
		this.m2 = m2;
	}

	public Matrix getM3() {
		return m3;
	}

	public void setM3(Matrix m3) {
		this.m3 = m3;
	}

	public Matrix getM4() {
		return m4;
	}

	public void setM4(Matrix m4) {
		this.m4 = m4;
	}
	
	public WakeLock getmWakeLock() {
		return mWakeLock;
	}

	public void setmWakeLock(WakeLock mWakeLock) {
		this.mWakeLock = mWakeLock;
	}

	public int getCompassErrorDisplayed() {
		return compassErrorDisplayed;
	}

	public void setCompassErrorDisplayed(int compassErrorDisplayed) {
		this.compassErrorDisplayed = compassErrorDisplayed;
	}

	public String getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(String zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public int getZoomProgress() {
		return zoomProgress;
	}

	public void setZoomProgress(int zoomProgress) {
		this.zoomProgress = zoomProgress;
	}

	public TextView getSearchNotificationTxt() {
		return searchNotificationTxt;
	}

	public void setSearchNotificationTxt(TextView searchNotificationTxt) {
		this.searchNotificationTxt = searchNotificationTxt;
	}	
}