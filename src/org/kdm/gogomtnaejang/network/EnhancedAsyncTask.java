package org.kdm.gogomtnaejang.network;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * 개선된 AsyncTask.
 *
 * ○ android.os.AsyncTask와 다른점
 * - Activity에 대한 약한 참조(weak reference) 유지
 * - onPreExecute에서 '작업처리중' 프로그레스 다이얼로그 자동 시작
 * - onPostExecute에서 프로그레스 다이얼로그 자동 종료
 * - doInBackground에서 에러발생시(throws exception) 프로그레스 다이얼로그 
 *   자동 종료 및 에러메시지 토스트 보여줌
 * - 참고로 doInBackground에서 에러발생시 하위 클래스의 onPostExecute는 실행되지 않음
 * - '작업처리중' 프로그레스 다이얼로그에서 사용자가 취소키 누르면 onCancelled 실행
 * - onCancelled에서 프로그레스 다이얼로그 자동 종료 및 작업취소 메시지 보여줌
 *
 * - 참조 : 
 * http://android-developers.blogspot.com/2010/07/multithreading-for-performance.html
 * http://tigerwoods.tistory.com/28
 *
 */
public abstract class EnhancedAsyncTask<Params, Progress, Result, WeakTarget extends Activity>
        extends AsyncTask<Params, Progress, Result> {

    protected WeakReference<WeakTarget> mTarget;
    protected Throwable mException = null;
    protected ProgressDialog mProgressDialog;

    /** 작업처리중 다이얼로그에 보여줄 메시지 */
    protected String mTaskProcessingMessage = "처리중입니다...";//처리중입니다. 잠시만 기다리십시오...

    /** 작업취소시 보여줄 메시지 */
    protected String mTaskCancelledMessage = "취소되었습니다.";//작업이 취소되었습니다.

    /** 작업처리중 다이얼로그를 보여줄지 여부 */
    protected boolean mProgressDialogEnabled = true;

    public EnhancedAsyncTask(WeakTarget target) {
        mTarget = new WeakReference<WeakTarget>(target);
    }

    /** {@inheritDoc} */
    @Override
    protected final void onPreExecute() {
        final WeakTarget target = mTarget.get();
        if (target != null) {
            showProgress(target);
            this.onPreExecute(target);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected final Result doInBackground(Params... params) {
        final WeakTarget target = mTarget.get();
        if (target != null) {
            try {
                return this.doInBackground(target, params);
            } catch (Throwable t) {
                this.mException = t;
                return null;
            }
        } else {
            return null;
        }
    }

    /** {@inheritDoc} */
    @Override
    protected final void onPostExecute(Result result) {
        final WeakTarget target = mTarget.get();
        if (target != null) {
            if (mException != null) {
                showError(target, mException);
                return;
            }

            dismissProgress();
            this.onPostExecute(target, result);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onCancelled() {
        final WeakTarget target = mTarget.get();
        if (target != null) {
            showCancelMessage(target);
            this.onCancelled(target);
        }
    }

    /**
     * 작업중 다이얼로그 보여줌.
     * @param message
     */
    protected void showProgress(Context context) {
        if (mProgressDialogEnabled == false)
            return;

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                //BACK 키 누르면 다이얼로그 닫고 현재 작업 취소
                //NOTE: 다이얼로그 떠있는 상태에서는 BACK키 눌러도 onBackPressed()는 실행되지 않음
                EnhancedAsyncTask.this.cancel(true);
            }
        });

        mProgressDialog.setMessage(mTaskProcessingMessage);
        mProgressDialog.show();
    }

    /**
     * 작업중 다이얼로그 닫음
     */
    protected void dismissProgress() {
        if (mProgressDialogEnabled == false)
            return;

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 작업취소 메시지 표시. 작업중 다이얼로그는 자동 닫음.
     */
    protected void showCancelMessage(Context context) {
        dismissProgress();
        Toast.makeText(context, mTaskCancelledMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * 에러메시지 표시. 작업중 다이얼로그는 자동 닫음.
     * @param t
     */
    protected void showError(Context context, Throwable t) {
        dismissProgress();

        //TODO exception 종류에 따라 적절히 에러메시지 선택
        String errorMessage = "오류가 발생하였습니다.";

        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
    }

    //-------------- 하위 클래스에서 구현 또는 오버라이딩할 메소드들
    /**
     * 작업 전 처리. UI관련 처리 가능.
     * @param target
     */
    protected void onPreExecute(WeakTarget target) {
        // No default action
    }

    /**
     * 실제 백그라운드 작업처리.
     *
     * 여기서 Exception이 던져지면 onPostExecute에서 자동으로 해당 Exception이 처리됨.
     * 하위클래스에서 try/catch로 직접 exception을 잡아서 별도 처리가능.
     *
     * @param target
     * @param params
     * @return
     */
    protected abstract Result doInBackground(WeakTarget target, Params... params);

    /**
     * 작업 후 처리. UI관련 처리 가능.
     * @param target
     * @param result
     */
    protected void onPostExecute(WeakTarget target, Result result) {
        // No default action
    }

    /**
     * 작업 취소 처리. UI관련 처리 가능.
     * @param target
     */
    protected void onCancelled(WeakTarget target) {
        // // No default action
    }

}