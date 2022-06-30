package com.mertoenjosh.tabiandating.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.mertoenjosh.tabiandating.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PhotoFragment : Fragment() {
    //widgets
    //vars
    private lateinit var mOutputUri: Uri
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_photo, container, false)
        Log.d(TAG, "onCreateView: started.")

        //open the camera to take a picture
        val btnLaunchCamera = view.findViewById<View>(R.id.btnLaunchCamera) as Button
        btnLaunchCamera.setOnClickListener {
            Log.d(TAG, "onClick: attempting to launch camera.")
            openCamera()
        }
        return view
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //mOutputUri = Uri.fromFile(outputMediaFile())
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputUri)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult: done taking a photo.")
            Log.d(
                TAG,
                "onActivityResult: output uri: $mOutputUri"
            )
            if (data != null) {
                activity?.setResult(
                    NEW_PHOTO_REQUEST,
                    data.putExtra(
                        getString(R.string.intent_new_camera_photo),
                        mOutputUri.toString()
                    )
                )
                activity?.finish()
            }
        }
    }

    private fun outputMediaFile(): File? {
        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ), "TabianDating"
        )
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return File(
            mediaStorageDir.path + File.separator +
                    "IMG_" + timeStamp + ".jpg"
        )
    }

    fun bugs () {
        TODO(
            """
                - Fix saving and deleting from shared preferences.
                - Fix gallery fragment to display images from device.
                - Fix image capture intent.
                - Fully convert to kotlin.
                - Replace deprecated methods.
                
            """.trimIndent()
        )
    }

    companion object {
        private const val TAG = "PhotoFragment"

        //constant
        private const val CAMERA_REQUEST_CODE = 5090
        private const val NEW_PHOTO_REQUEST = 3567
    }
}
