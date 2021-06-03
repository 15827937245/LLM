package com.example.mycamrea.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.mycamrea.View.MyTextureView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraController {
    private final String TAG = "llm_CameraController";

    private static final boolean FLASH_OFF = false;
    private static final boolean FLASH_ON = true;

    //CameraManager：摄像头管理器，用于打开和关闭系统摄像头
    private CameraManager cameraManager;

    public static final float PREVIEW_SIZE_RATIO_OFFSET = 0.01f;

    private float currentRatio;

    public static final float RATIO_MODE_1 = 1.333f;
    public static final float RATIO_MODE_2 = 1.777f;

    public static final int CAMERA_MODE_TAKE_PICTURE = 0;
    public static final int CAMERA_MODE_TAKE_VIDEO = 1;

    private String cameraId;

    private Activity mActivity;
    private MyTextureView textureView;
    private Handler mHandle;

    private int  PhoneDeviceDegree;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;
    private boolean waterMarkState;
    private MediaRecorder mMediaRecorder;
    private boolean isVideoAlive;
    private int CameraMode;

    public void setPhoneDeviceDegree(int phoneDeviceDegree) {
        this.PhoneDeviceDegree = phoneDeviceDegree;
    }

    private boolean flashSwitch;
    public String CAMERA_LENS_FRONT = "1";
    public String CAMERA_LENS_BACK = "0";
    private ImageReader mImageReader;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mCameraDeviceCaptureRequest;
    private CameraCaptureSession mSession;
    private File mFile;
    private String mFilePath;


    private Size previewSize;
    private Size pictureSize;
    private Integer mOrientation;


    public CameraController(Activity activity, MyTextureView textureView, Handler mHandle) {
        this.mActivity = activity;
        this.textureView = textureView;
        this.mHandle = mHandle;
        init();
    }

    private void init() {
        flashSwitch = FLASH_OFF;
        cameraManager = (CameraManager) mActivity.getSystemService(Context.CAMERA_SERVICE);
        //获取摄像头参数
        cameraId = CAMERA_LENS_BACK;
        currentRatio = RATIO_MODE_1;
        //Log.i(TAG, "init: ("+previewSize.getWidth()+":"+previewSize.getHeight()+")");
        mFilePath = "DCIM/MyCamera/";
        CameraMode = CAMERA_MODE_TAKE_PICTURE;
        isVideoAlive = false;
    }

    private void getCameraLensFacing() {
        try {
            mOrientation = cameraManager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.SENSOR_ORIENTATION);
            //Log.i(TAG, "getCameraLensFacing: orientation:"+orientation);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void openCamera() {
        closeCamera();
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            cameraManager.openCamera(cameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    //获取CameraDevice
                    mCameraDevice = camera;
                    getCameraLensFacing();

                    if (CameraMode == CAMERA_MODE_TAKE_PICTURE){
                    openPreviewMode();
                    }else {
                        openVideoMode();
                    }
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                       camera.close();
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                            camera.close();
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void cratePreviewSession(){
        getSize(cameraId,currentRatio);
        crateImageReader();
        Log.i(TAG, "createMediaRecordSession: "+previewSize.getWidth()+":"+previewSize.getHeight());
        mActivity.runOnUiThread(()->textureView.changeSize(previewSize));
        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        surfaceTexture.setDefaultBufferSize(previewSize.getWidth(),previewSize.getHeight());
        Surface surface = new Surface(surfaceTexture);
        List<Surface> surfaceList = new ArrayList<>();
        surfaceList.add(surface);
        surfaceList.add(mImageReader.getSurface());
        try {
            mCameraDeviceCaptureRequest = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mCameraDeviceCaptureRequest.addTarget(surface);
            Log.i(TAG, "cratePreviewSession: "+Thread.currentThread().getName());
            mCameraDevice.createCaptureSession(surfaceList, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    mSession = session;
                    Log.i(TAG, "onConfigured: "+Thread.currentThread().getName());
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            },mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


    }

    private void createMediaRecordSession(){
        getSize(cameraId,RATIO_MODE_2);
        createMediaRecord();
        Log.i(TAG, "createMediaRecordSession: "+previewSize.getWidth()+":"+previewSize.getHeight());
        mActivity.runOnUiThread(()->textureView.changeSize(previewSize));
        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        surfaceTexture.setDefaultBufferSize(previewSize.getWidth(),previewSize.getHeight());
        Surface surface = new Surface(surfaceTexture);
        ArrayList<Surface> surfaces =new ArrayList<>();
        surfaces.add(surface);
        surfaces.add(mMediaRecorder.getSurface());
        try {
            mCameraDeviceCaptureRequest = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
            mCameraDeviceCaptureRequest.addTarget(surface);
            mCameraDeviceCaptureRequest.addTarget(mMediaRecorder.getSurface());
            Log.i(TAG, "createMediaRecordSession: "+Thread.currentThread().getName());
            mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    Log.i(TAG, "onConfigured: "+Thread.currentThread().getName());
                    mSession = session;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                }
            },mBackgroundHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createMediaRecord(){
        mMediaRecorder = new MediaRecorder();
        mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),mFilePath + "VID_"+System.currentTimeMillis()+ ".mp4");

       //Initial
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);

        //Initialized
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        //DataSourceConfigured

        mMediaRecorder.setOutputFile(mFile.getPath());
        mMediaRecorder.setVideoEncodingBitRate(10000000);
        mMediaRecorder.setVideoFrameRate(30);

        mMediaRecorder.setVideoSize(previewSize.getWidth(),previewSize.getHeight());
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);


        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crateImageReader(){
        mImageReader = ImageReader.newInstance(pictureSize.getWidth(),pictureSize.getHeight(), ImageFormat.JPEG,1);
        mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Log.i(TAG, "onImageAvailable: --------------");
               savePicture(reader.acquireNextImage());
            }
        },mBackgroundHandler);
    }

    private Bitmap addWaterMark(Bitmap bitmapSrc){
        Bitmap bitmapNew = Bitmap.createBitmap(bitmapSrc.getWidth(), bitmapSrc.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasNew = new Canvas(bitmapNew);
        canvasNew.drawBitmap(bitmapSrc, 0, 0, null);
        Paint paintText = new Paint();
        paintText.setColor(0xFFBB86FC);
        if (lenFaceFront()) {
            paintText.setTextSize(100);
        } else {
            paintText.setTextSize(200);
        }
        paintText.setDither(true);
        paintText.setFilterBitmap(true);
        Rect rectText = new Rect();
        String drawTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        paintText.getTextBounds(drawTime, 0, drawTime.length(), rectText);
        int beginX = bitmapNew.getWidth() - rectText.width() - 100;
        int beginY = bitmapNew.getHeight() - rectText.height();
        canvasNew.drawText(drawTime, beginX, beginY, paintText);
        return bitmapNew;
    }

    public void setWaterMarkState(boolean waterMarkState){
        this.waterMarkState = waterMarkState;
    }

    private void savePicture(Image image) {
        Matrix matrix = new Matrix();
        matrix.postRotate(getJpegRotation(cameraId,PhoneDeviceDegree));
        if (lenFaceFront()) {
            matrix.postScale(-1, 1);
        }
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        Bitmap bitmapSrc = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        if (waterMarkState){
            bitmapSrc = addWaterMark(bitmapSrc);
        }
        options.inSampleSize = 4;//采样率 inSampleSize 的值要求必须大于1，且只能是2的整数倍
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        Bitmap bitmapShow = Bitmap.createBitmap(bitmap2, 0, 0,bitmap2.getWidth(),
                bitmap2.getHeight(), matrix, true);
        Message message = new Message();
        message.what = 0;
        message.obj = bitmapShow;
        mHandle.sendMessage(message);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(mFile);
            bitmapSrc.compress(Bitmap.CompressFormat.JPEG,100,os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            Message message1 = new Message();
            message1.what = 1;
            message1.obj = mFile;
            mHandle.sendMessage(message1);
            try {
                image.close();
                if (null != os) {
                    os.close();
                }
            }catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    private void openFlash(){
        mCameraDeviceCaptureRequest.set(CaptureRequest.FLASH_MODE,CaptureRequest.FLASH_MODE_TORCH);
        updatePreview();
    }

    private void closeFlash(){
        mCameraDeviceCaptureRequest.set(CaptureRequest.FLASH_MODE,CaptureRequest.FLASH_MODE_OFF);
        updatePreview();
    }

    private void updatePreview() {
        Message message = new Message();
        message.what = 3;
        message.obj = previewSize;
        mHandle.sendMessage(message);
        try {
            mSession.setRepeatingRequest(mCameraDeviceCaptureRequest.build(), new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
                    super.onCaptureStarted(session, request, timestamp, frameNumber);
                }

                @Override
                public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
                    super.onCaptureProgressed(session, request, partialResult);

                }

                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    result.get(CaptureResult.CONTROL_AF_STATE);
                    result.get(CaptureResult.FLASH_STATE);
                }

                @Override
                public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
                    super.onCaptureFailed(session, request, failure);
                }

                @Override
                public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession session, int sequenceId, long frameNumber) {
                    super.onCaptureSequenceCompleted(session, sequenceId, frameNumber);
                }

                @Override
                public void onCaptureSequenceAborted(@NonNull CameraCaptureSession session, int sequenceId) {
                    super.onCaptureSequenceAborted(session, sequenceId);
                }

                @Override
                public void onCaptureBufferLost(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull Surface target, long frameNumber) {
                    super.onCaptureBufferLost(session, request, target, frameNumber);
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public int getJpegRotation(String cameraId, int orientation) {

        int rotation = 0;
        if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
            orientation = 0;
        }
        CameraCharacteristics cameraInfo = null;
        try {
            cameraInfo = cameraManager.getCameraCharacteristics(cameraId);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        if (cameraInfo.get(CameraCharacteristics.LENS_FACING) ==
                CameraCharacteristics.LENS_FACING_FRONT) {//front camera
            rotation = (mOrientation - orientation + 360) % 360;
        } else {// back-facing camera
            rotation = (mOrientation + orientation + 360) % 360;
        }
        return rotation;
    }

    public boolean lenFaceFront() {
        CameraCharacteristics cameraInfo = null;
        try {
            cameraInfo = cameraManager.getCameraCharacteristics(cameraId);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        if (cameraInfo.get(CameraCharacteristics.LENS_FACING) ==
                CameraCharacteristics.LENS_FACING_FRONT) {//front camera
            return true;
        }

        return false;
    }

    public void takePicture(){
        mFile =  new File(Environment.getExternalStorageDirectory().getAbsolutePath(), mFilePath+ "IMG_"+System.currentTimeMillis() + ".jpg");
        CaptureRequest.Builder captureBuilder = null;
        try {
            captureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(mImageReader.getSurface());
            //captureBuilder.addTarget(new Surface(textureView.getSurfaceTexture()));

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        try {
            Log.i(TAG, "takePicture: --------------");
            mSession.capture(captureBuilder.build(),null,mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean flashSwitch(){
        //Log.i(TAG, "flashSwitch: "+flashSwitch);
        if (flashSwitch){
            flashSwitch = FLASH_OFF;
            closeFlash();
        }else {
            flashSwitch = FLASH_ON;
            openFlash();
        }
        return flashSwitch;
    }

    private void getSize(String CameraId,float targetRatio){
        CameraCharacteristics cameraCharacteristics = null;
        try {
            cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
            //cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        //获取摄像头所支持的Map配置集合
        StreamConfigurationMap streamConfigurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
       //获取预览所支持的size集合
        Size[] previewSizeMap = streamConfigurationMap.getOutputSizes(SurfaceTexture.class);
        //获取图片所支持的size集合
        Size[] captureSizeMap = streamConfigurationMap.getOutputSizes(ImageFormat.JPEG);

        Size[] videoSizes = streamConfigurationMap.getOutputSizes(MediaRecorder.OutputFormat.MPEG_4);
        //获取屏幕的宽度
        int widthPixels = mActivity.getResources().getDisplayMetrics().widthPixels;


        float minOfSize = Integer.MAX_VALUE;
        for (int i = 0; i < previewSizeMap.length ; i++) {
           float ratio = (float) previewSizeMap[i].getWidth()/(float)previewSizeMap[i].getHeight();
            //Log.i(TAG, "getSize: previewSizeMap[i]:("+previewSizeMap[i].getWidth()+":"+previewSizeMap[i].getHeight()+")");
           // Log.i(TAG, "getSize: "+Math.abs(ratio-targetRatio));
            //Log.i(TAG, "getSize: (Math.abs(ratio-targetRatio))<PREVIEW_SIZE_RATIO_OFFSET:"+(Math.abs(ratio-targetRatio)<PREVIEW_SIZE_RATIO_OFFSET));
           if ((Math.abs(ratio-targetRatio))<PREVIEW_SIZE_RATIO_OFFSET){
               //Log.i(TAG, "getSize: previewSizeMap[i]:("+previewSizeMap[i].getWidth()+":"+previewSizeMap[i].getHeight()+")");
               int diff = Math.abs(previewSizeMap[i].getHeight() - widthPixels);
               if (diff < minOfSize) {
                   previewSize = previewSizeMap[i];
                   minOfSize = diff;
               } else if ((diff == minOfSize) && (previewSizeMap[i].getHeight() > widthPixels)) {
                   previewSize = previewSizeMap[i];
               }
           }
        }

        pictureSize = new Size(0, 0);
        for (int i = 0; i < captureSizeMap.length; i++) {
            float ratio =(float)captureSizeMap[i].getWidth() / (float) captureSizeMap[i].getHeight();
            if (Math.abs(ratio - targetRatio) > PREVIEW_SIZE_RATIO_OFFSET) {
                continue;
            }
            if (captureSizeMap[i].getWidth() * captureSizeMap[i].getHeight() >= pictureSize.getWidth() * pictureSize.getHeight()) {
                pictureSize = captureSizeMap[i];
            }
        }


    }

    public float changeRatio(){
            if (currentRatio==RATIO_MODE_1){
                currentRatio = RATIO_MODE_2;
            }else {
                currentRatio = RATIO_MODE_1;
            }
            openPreviewMode();
            return currentRatio;
    }

    private void closeSession(){
        if (mSession!=null){
            mSession.close();
            mSession = null;
        }
    }

    private void closeImageReader(){
        if (mImageReader!=null){
            mImageReader.close();
            mImageReader = null;
        }
    }

    private void closeMediaRecorder(){
        if (mMediaRecorder!=null){
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }
    

    public void closePreviewMode(){
        closeImageReader();
        closeSession();
    }

    public void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    public int openPreviewMode(){
        CameraMode = CAMERA_MODE_TAKE_PICTURE;
        closeVideoMode();
        closePreviewMode();
        cratePreviewSession();
        return CameraMode;
    }

    public void lensReversal(){
        if (cameraId==CAMERA_LENS_BACK){
            cameraId = CAMERA_LENS_FRONT;
        }else {
            cameraId = CAMERA_LENS_BACK;
        }
        openCamera();
    }

    public void closeCamera(){
        if (mCameraDevice!=null){
            mCameraDevice.close();
            mCameraDevice=null;
        }
    }

    public int openVideoMode(){
        CameraMode = CAMERA_MODE_TAKE_VIDEO;
        closeMediaRecorder();
        closePreviewMode();
        createMediaRecordSession();
        return  CameraMode;
    }

    public void closeVideoMode(){
        closeMediaRecorder();
        closeSession();
    }

    public boolean startVideo(){
        if (isVideoAlive){
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            openVideoMode();
            isVideoAlive = false;
        }else {
            mMediaRecorder.start();
            isVideoAlive = true;
        }
       return isVideoAlive;
    }
    /*public Size getPreviewSize(){
        Log.i(TAG, "getPreviewSize: ("+previewSize.getWidth()+":"+previewSize.getHeight()+")");
        return previewSize;
    }*/

    public void startFocus(float x,float y){
        mCameraDeviceCaptureRequest.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AF_TRIGGER_IDLE);
        mCameraDeviceCaptureRequest.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_IDLE);
        mCameraDeviceCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE,CaptureRequest.CONTROL_AF_MODE_AUTO);
        mCameraDeviceCaptureRequest.set(CaptureRequest.CONTROL_AF_TRIGGER,CaptureRequest.CONTROL_AF_TRIGGER_START);
        mCameraDeviceCaptureRequest.set(CaptureRequest.CONTROL_AF_REGIONS,new MeteringRectangle[]{new MeteringRectangle((int)(x-100),(int)(y-100),200,200,MeteringRectangle.METERING_WEIGHT_DONT_CARE)});
        updatePreview();
    }
}
