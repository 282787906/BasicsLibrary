package com.liqg.basicslibrary.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liqg.basicslibrary.R;
import com.liqg.basicslibrary.http.DownloadFile;
import com.liqg.basicslibrary.http.SubmitCommon;
import com.liqg.library.business.Permission.CheckPermissionCallback;
import com.liqg.library.business.Permission.Permission;
import com.liqg.library.http.RequestType;
import com.liqg.library.http.ResponseCallback;
import com.liqg.library.http.ResponseCallbackProgress;
import com.liqg.library.utils.LiEnvironment;
import com.liqg.library.utils.ToastUtil;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.OnUploadListener;

import java.io.File;
import java.util.List;

public class HttpActivity extends BaseActivity implements View.OnClickListener {

    protected ProgressBar nohttpProgressBar;
    protected EditText nohttpEtUrl;
    protected Button nohttpButGet;
    protected Button nohttpButGetHead;
    protected Button nohttpButDownload;
    protected Button nohttpButUpload;
    protected TextView nohttpTvMsg;
    protected EditText nohttpEtDownLoad;
    protected EditText nohttpEtUpload;
    protected Button nohttpButDownload2SD;

    String DOMAIN = "http://api.nohttp.net";
    public final String GET = DOMAIN + "/jsonObject?name=yanzhenjie&pwd=123";
    //    public final String DOWNLOAD = "https://codeload.github.com/282787906/BasicsLibrary/zip/master";  //150kb
    public final String DOWNLOAD = "http://downloads.iaxure.com/IaxureRP8V1.2.zip";    //6000kb
    public final String UPLOAD = DOMAIN + "/upload";
    public String DOWNLOAD_FILE_PATH;
    public final String DOWNLOAD_FILE_NAME = "download";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_http);
        initView();
        nohttpEtUrl.setText(GET);
        nohttpEtDownLoad.setText(DOWNLOAD);

        nohttpEtUpload.setText(UPLOAD);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.nohttp_but_Get) {
            final SubmitCommon submit = new SubmitCommon(mContext, RequestType.GET, nohttpEtUrl.getText().toString());
            submit.submit(new ResponseCallback() {
                @Override
                public void onSuccess() {
                    ToastUtil.longToast(mContext, (String) submit.getAnalysisResult());
                    nohttpTvMsg.setText((String) submit.getAnalysisResult());

                }

                @Override
                public void onFail(int errorCode, String errorMsg) {
                    ToastUtil.longToast(mContext, errorMsg);
                    nohttpTvMsg.setText(errorMsg);
                }
            });
        } else if (view.getId() == R.id.nohttp_but_GetHead) {
            final SubmitCommon submit = new SubmitCommon(mContext, RequestType.HEAD, nohttpEtUrl.getText().toString());
            submit.submit(new ResponseCallback() {
                @Override
                public void onSuccess() {
                    ToastUtil.longToast(mContext, "" + submit.getStatusCode());

                    nohttpTvMsg.setText("onSuccess  " + submit.getStatusCode());
                }

                @Override
                public void onFail(int errorCode, String errorMsg) {
                    ToastUtil.longToast(mContext, errorMsg);

                    nohttpTvMsg.setText("onFail  " + errorCode + "   " + errorMsg);
                }
            });
        } else if (view.getId() == R.id.nohttp_but_Download) {


            DOWNLOAD_FILE_PATH = LiEnvironment.getAppRoot(mContext);

            Log.d(TAG,"下载目录:"+DOWNLOAD_FILE_PATH);

           download();
        } else if (view.getId() == R.id.nohttp_but_upload) {
            uploadFile(DOWNLOAD_FILE_PATH + "/" + DOWNLOAD_FILE_NAME, DOWNLOAD);
        } else if (view.getId() == R.id.nohttp_but_Download2SD) {

//            if (checkSDPermission()){
//                DOWNLOAD_FILE_PATH = LiEnvironment.getSdCardRoot(mContext);
//
//                ToastUtil.shortToast(mContext," checkSDPermission  true");
//            }else {
//                ToastUtil.shortToast(mContext," checkSDPermission  false");
//            }

            checkPermission(new Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,"下载"), new CheckPermissionCallback() {


                @Override
                public void LowerThan_M(List<Permission> permissionList) {
                    if (checkSDPermission()){
                        DOWNLOAD_FILE_PATH = LiEnvironment.getSdCardRoot(mContext);
                        Log.d(TAG,"6.0以下 权限通过，下载目录:"+DOWNLOAD_FILE_PATH);
                        download();
                    }else {
                        showPremissionDialog(permissionList,null);
                    }
                }

                @Override
                public void checkOverPass() {
                    DOWNLOAD_FILE_PATH = LiEnvironment.getSdCardRoot(mContext);
                    Log.d(TAG,"下载目录:"+DOWNLOAD_FILE_PATH);
                    download();
                }

                @Override
                public void checkOverCancel() {
                    ToastUtil.syncToast(mContext, "权限被拒绝，该功能无法使用", handler, 2000, new ToastUtil.SyncToastCallback() {
                        @Override
                        public void onHide() {

                        }
                    });
                }
            });
        }
    }



    private void download() {
        DownloadFile downloadFile = new DownloadFile(mContext, RequestType.GET,
                nohttpEtDownLoad.getText().toString()
                , DOWNLOAD_FILE_PATH,
                DOWNLOAD_FILE_NAME);
        downloadFile.submit(new ResponseCallbackProgress() {
            @Override
            public void onSuccess() {
                ToastUtil.shortToast(mContext, "Download Success");

                nohttpTvMsg.setText("Download Success");
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {

                nohttpTvMsg.setText("Download Fail:" + errorCode + "  " + errorMsg);
                ToastUtil.shortToast(mContext, "Download Fail:" + errorCode + "  " + errorMsg);
            }

            @Override
            public void onProgress(int progress, long fileCount) {
                Log.d(TAG, progress + "");
                nohttpProgressBar.setProgress(progress);
            }
        });
    }

    private void uploadFile(String path, String url) {
        File file = new File(path);
        Log.d(TAG, "file.length:" + file.length());

        FileBinary binary = new FileBinary(file);
        binary.setUploadListener(12138, mOnUploadListener);

        SubmitCommon submit = new SubmitCommon(mContext, RequestType.POST, url);
        submit.requestParams.put("name", "name");
        submit.requestParams.put("head", binary);

        submit.submit(new ResponseCallback() {
            @Override
            public void onSuccess() {
                //mAdapter.notify();
            }

            @Override
            public void onFail(int code, String s) {
                Log.i(TAG, "CommonSubmit  onFail" + code);
            }
        });

    }

    private OnUploadListener mOnUploadListener = new OnUploadListener() {
        @Override
        public void onStart(int what) {// 文件开始上传。
            nohttpTvMsg.setText("OnUploadListener onStart ");
        }


        @Override
        public void onCancel(int what) {// 文件的上传被取消时。
        }

        @Override
        public void onProgress(int what, int progress) {// 文件的上传进度发生变化。
            nohttpProgressBar.setProgress(progress);
            Log.i(TAG, "OnUploadListener onProgress " + what + "   progress:" + progress);

        }

        @Override
        public void onFinish(int what) {// 文件上传完成
            nohttpTvMsg.setText("OnUploadListener onFinish " + what);

            Log.i(TAG, "OnUploadListener onFinish " + what);
        }

        @Override
        public void onError(int what, Exception exception) {// 文件上传发生错误。
            nohttpTvMsg.setText("OnUploadListener onError " + exception.getMessage());
            Log.e(TAG, "OnUploadListener onError " + exception.getMessage());
        }
    };

    private void initView() {
        nohttpProgressBar = (ProgressBar) findViewById(R.id.nohttp_ProgressBar);
        nohttpEtUrl = (EditText) findViewById(R.id.nohttp_et_url);
        nohttpButGet = (Button) findViewById(R.id.nohttp_but_Get);
        nohttpButGet.setOnClickListener(HttpActivity.this);
        nohttpButGetHead = (Button) findViewById(R.id.nohttp_but_GetHead);
        nohttpButGetHead.setOnClickListener(HttpActivity.this);
        nohttpButDownload = (Button) findViewById(R.id.nohttp_but_Download);
        nohttpButDownload.setOnClickListener(HttpActivity.this);
        nohttpButUpload = (Button) findViewById(R.id.nohttp_but_upload);
        nohttpButUpload.setOnClickListener(HttpActivity.this);
        nohttpTvMsg = (TextView) findViewById(R.id.nohttp_tv_Msg);
        nohttpEtDownLoad = (EditText) findViewById(R.id.nohttp_et_DownLoad);
        nohttpEtUpload = (EditText) findViewById(R.id.nohttp_et_Upload);
        nohttpButDownload2SD = (Button) findViewById(R.id.nohttp_but_Download2SD);
        nohttpButDownload2SD.setOnClickListener(HttpActivity.this);
    }
}
