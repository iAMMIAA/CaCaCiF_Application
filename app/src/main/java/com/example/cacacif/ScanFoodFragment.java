package com.example.cacacif;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;

public class ScanFoodFragment extends Fragment {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scan_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PreviewView previewView = view.findViewById(R.id.previewView);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            startCamera(previewView);
        } else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PreviewView previewView = requireView().findViewById(R.id.previewView);
                startCamera(previewView);
            } else {
                Toast.makeText(requireContext(), "Bạn đã từ chối cấp quyền truy cập camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera(PreviewView previewView) {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider, previewView);
            } catch (ExecutionException | InterruptedException e) {
                Log.e("CameraFragment", "Error setting up camera", e);
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider, @NonNull PreviewView previewView) {
        Preview preview = new Preview.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview);
    }
//    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_scan_food, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        PreviewView previewView = view.findViewById(R.id.previewView);
//
//        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
//        cameraProviderFuture.addListener(() -> {
//            try {
//                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
//                bindPreview(cameraProvider, previewView);
//            } catch (ExecutionException | InterruptedException e) {
//                Log.e("CameraFragment", "Error setting up camera", e);
//            }
//        }, ContextCompat.getMainExecutor(requireContext()));
//    }
//
//    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider, @NonNull PreviewView previewView) {
//        Preview preview = new Preview.Builder().build();
//
//        CameraSelector cameraSelector = new CameraSelector.Builder()
//                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//                .build();
//
//        preview.setSurfaceProvider(previewView.getSurfaceProvider());
//
//        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview);
//    }
}