package com.example.permissionsapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.permissionsapp.databinding.PhotoFragmentBinding
import com.example.permissionsapp.di.DaggerAppComponent
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService


class PhotoFragment : Fragment() {
    private var imageCapture: ImageCapture? = null
    private lateinit var executor: ExecutorService
    private var _binding: PhotoFragmentBinding? = null
    private val viewModel by viewModels<ImagesViewModel> {
        DaggerAppComponent.create().viewModelFactory()
    }
    private val binding get() = _binding!!
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && it.value == false) permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(context, "Permission request denied", Toast.LENGTH_SHORT).show()
            } else startCamera()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (allPermissionsGranted()) startCamera()
        else requestPermissions()
    }

    private fun requestPermissions() {
        launcher.launch(REQUIRED_PERMISSIONS)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build().also { it.setSurfaceProvider(binding.viewFinder.surfaceProvider) }

            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        context?.let { thisContext ->
            ContextCompat.checkSelfPermission(
                thisContext, it
            )
        } == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PhotoFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photoButton.setOnClickListener {
            takePhoto()
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US) //name for saved image
            .format(System.currentTimeMillis())

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/")
            }
        }
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireActivity().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, exception.message.toString())
                }

                @SuppressLint("RestrictedApi")
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded  : ${outputFileResults.savedUri}"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, msg)
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.addPhoto(outputFileResults.savedUri.toString())
                    }
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        executor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXApp"
        const val FILENAME_FORMAT = "yyyy_MM_dd_HH-mm-ss"
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

    }

}