package com.mertoenjosh.tabiandating.settings

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mertoenjosh.tabiandating.R
import com.mertoenjosh.tabiandating.adapters.GridImageAdapter
import com.mertoenjosh.tabiandating.util.FileSearch
import java.io.File

class GalleryFragment : Fragment() {
    //widgets
    private var gridView: GridView? = null
    private var galleryImage: ImageView? = null
    private var directorySpinner: Spinner? = null

    //vars
    private var directories: ArrayList<String>? = null
    private var mSelectedImage: String? = null
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_gallery, container, false)
        galleryImage = view.findViewById<View>(R.id.galleryImageView) as ImageView
        gridView = view.findViewById<View>(R.id.gridView) as GridView
        directorySpinner = view.findViewById<View>(R.id.spinnerDirectory) as Spinner
        directories = ArrayList()
        Log.d(TAG, "onCreateView: started.")
        val close = view.findViewById<View>(R.id.close) as ImageView
        close.setOnClickListener {
            Log.d(
                TAG,
                "onClick: closing the gallery fragment."
            )
            activity?.setResult(NEW_PHOTO_REQUEST)
            activity?.finish()
        }
        val choose = view.findViewById<View>(R.id.choose) as TextView
        choose.setOnClickListener {
            Log.d(TAG, "onClick: photo has been chosen.")
            if (mSelectedImage != "") {
                activity?.setResult(
                    NEW_PHOTO_REQUEST,
                    activity?.intent?.putExtra(getString(R.string.intent_new_gallery_photo), mSelectedImage)
                )
                activity?.finish()
            }
        }
        init()
        return view
    }

    private fun init() {
        val rootDir = Environment.getExternalStorageDirectory().path

        //check for other folders inside "/storage/emulated/0/pictures"
        val picturesDir = rootDir + File.separator + "Pictures"
        directories!!.add(picturesDir)
        if (FileSearch.getDirectoryPaths(picturesDir) != null) {
            directories = FileSearch.getDirectoryPaths(picturesDir)
        }
        val cameraDir = rootDir + File.separator + "DCIM" + File.separator + "Camera"
        directories!!.add(cameraDir)
        val directoryNames = ArrayList<String>()
        for (i in directories!!.indices) {
            Log.d(TAG, "init: directory: " + directories!![i])
            val index = directories!![i].lastIndexOf("/")
            val string = directories!![i].substring(index)
            directoryNames.add(string)
        }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, directoryNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        directorySpinner!!.adapter = adapter
        directorySpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                Log.d(TAG, "onItemClick: selected: " + directories!![position])

                //setup our image grid for the directory chosen
                setupGridView(directories!![position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupGridView(selectedDirectory: String) {
        Log.d(
            TAG,
            "setupGridView: directory chosen: $selectedDirectory"
        )
        val imgURLs: ArrayList<String> = FileSearch.getFilePaths(selectedDirectory)
        if (imgURLs.size > 0) {
            //set the grid column width
            val gridWidth: Int = resources.displayMetrics.widthPixels
            val imageWidth = gridWidth / NUM_GRID_COLUMNS
            gridView!!.columnWidth = imageWidth

            //use the grid adapter to adapter the images to gridview
            val adapter = GridImageAdapter(requireContext(), R.layout.layout_grid_imageview, imgURLs)
            gridView!!.adapter = adapter

            //set the first image to be displayed when the activity fragment view is inflated
            try {
                setImage(imgURLs[0], galleryImage)
                mSelectedImage = imgURLs[0]
            } catch (e: ArrayIndexOutOfBoundsException) {
                Log.e(TAG, "setupGridView: ArrayIndexOutOfBoundsException: " + e.message)
            }
            gridView!!.onItemClickListener =
                OnItemClickListener { parent, view, position, id ->
                    Log.d(TAG, "onItemClick: selected an image: " + imgURLs[position])
                    setImage(imgURLs[position], galleryImage)
                    mSelectedImage = imgURLs[position]
                }
        }
    }

    private fun setImage(imgURL: String, imageView: ImageView?) {
        Log.d(TAG, "setImage: setting image")
        if (imageView != null) {
            Glide.with(requireActivity())
                .load(imgURL)
                .into(imageView)
        }
    }

    companion object {
        private const val TAG = "GalleryFragment"

        //constants
        private const val NUM_GRID_COLUMNS = 3
        private const val NEW_PHOTO_REQUEST = 3567
    }
}