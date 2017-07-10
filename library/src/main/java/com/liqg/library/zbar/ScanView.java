package com.liqg.library.zbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dtr.zbar.build.ZBarDecoder;
import com.liqg.library.R;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by liqg
 * 2016/12/20 09:37
 * Note :
 */
public class ScanView extends RelativeLayout {

    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    private CameraManager mCameraManager;
    private FrameLayout scanPreview;
    private RelativeLayout scanContainer;//全屏背景预览
    private RelativeLayout scanCropView;//识别区域
    private Context mContext;  private View mViewTop;

    private Rect mCropRect = null;
    private boolean barcodeScanned = false;
    private boolean previewing = true;

    private int autoFocusDelayMillis = 500;

    float marginTop;
    float height;
    float width;

    int bgId;

    public ScanView(Context context, AttributeSet attrs ) {
        super(context, attrs);
        mContext = context;
        initView(attrs);
    }
    private void initView(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.ScanView,0, 0);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;// 屏幕宽度（像素）
        bgId = a.getResourceId(R.styleable.ScanView_scanBg, android.R.color.transparent);
        marginTop = a.getDimension(R.styleable.ScanView_marginTop, (float) (widthPixels*0.2));
        height = a.getDimension(R.styleable.ScanView_height,  (float) (widthPixels*0.8));
        width = a.getDimension(R.styleable.ScanView_width, (float) (widthPixels*0.8));

        LayoutInflater.from(mContext).inflate(R.layout.scan_view, this, true);
        mViewTop   =  findViewById(R.id.scan_view_view_top);
        scanPreview = (FrameLayout) findViewById(R.id.scan_view_rl_preView);
        scanContainer = (RelativeLayout) findViewById(R.id.scan_view_rl_container);
        scanCropView = (RelativeLayout) findViewById(R.id.scan_view_rl_cropView);
        mViewTop.getLayoutParams().height= (int) marginTop;
        scanCropView.getLayoutParams().height= (int) height;
        scanCropView.getLayoutParams().width= (int) width;
        scanCropView.setBackgroundResource(bgId);

    }

    OnScanListener mOnScanListener;

    public void init(OnScanListener onScanListener) {
        mOnScanListener = onScanListener;
        initCamera();
    }
    public void reScan() {
        if (barcodeScanned) {
            barcodeScanned = false;
            mCamera.setPreviewCallback(previewCb);
            mCamera.startPreview();
            previewing = true;
            mCamera.autoFocus(autoFocusCB);

            mOnScanListener.onScanStart();
        }
    }

    private void initCamera() {
        autoFocusHandler = new Handler();
        if (mCameraManager == null || !mCameraManager.isOpen()) {
            mCameraManager = new CameraManager(mContext);
            try {
                mCameraManager.openDriver();
            } catch (IOException e) {
                mOnScanListener.onScanError(e,"启动相机失败");
                return;
            }
        }
        if (mOnScanListener!=null){
        mOnScanListener.onScanStart();}
        mCamera = mCameraManager.getCamera();
        mPreview = new CameraPreview(mContext, mCamera, previewCb, autoFocusCB);
        scanPreview.addView(mPreview);

    }

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, autoFocusDelayMillis);
        }
    };
    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };
    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Size size = camera.getParameters().getPreviewSize();

            // 这里需要将获取的data翻转一下，因为相机默认拿的的横屏的数据
            byte[] rotatedData = new byte[data.length];
            for (int y = 0; y < size.height; y++) {
                for (int x = 0; x < size.width; x++)
                    rotatedData[x * size.height + size.height - y - 1] = data[x + y * size.width];
            }

            // 宽高也要调整
            int tmp = size.width;
            size.width = size.height;
            size.height = tmp;

            initCrop();
            ZBarDecoder zBarDecoder = new ZBarDecoder();
            /////////////////////////////////////////////////////////////////////////////////////////top  403  bottom  815      412
            /////////////////////////////////////////////////////////////////////////////////////////top  249  bottom  623      374
            /////////////////////////////////////////////////////////////////////////////////////////top  190  bottom  550      360

            String result = zBarDecoder.decodeCrop(rotatedData, size.width, size.height, mCropRect.left, mCropRect.top, mCropRect.width(), mCropRect.height());

            if (!TextUtils.isEmpty(result)) {

                barcodeScanned = true;
                previewing = false;
                //  mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                if (mOnScanListener != null) {
                    mOnScanListener.onScanSuccess(result);
                }
            }
        }
    };

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = mCameraManager.getCameraResolution().y;
        int cameraHeight = mCameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void onResume() {
        if (mCamera == null) {
            previewing = true;
            initCamera();
        }

    }

    public void onPause() {

        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
            mCameraManager.closeDriver();
            mCamera = null;
            scanPreview.removeAllViews();
        }
    }

}
