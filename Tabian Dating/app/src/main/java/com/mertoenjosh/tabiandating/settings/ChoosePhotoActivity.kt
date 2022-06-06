package com.mertoenjosh.tabiandating.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mertoenjosh.tabiandating.R
import com.mertoenjosh.tabiandating.adapters.MyPagerAdapter

class ChoosePhotoActivity : AppCompatActivity() {
    private val GALLERY_FRAGMENT = 0
    private val PHOTO_FRAGMENT = 1

    //fragments
    private var galleryFragment: GalleryFragment? = null
    private var photoFragment: PhotoFragment? = null

    //widgets
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_photo)
        viewPager = findViewById(R.id.viewpager_container)
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter: MyPagerAdapter = MyPagerAdapter(supportFragmentManager)
        galleryFragment = GalleryFragment()
        photoFragment = PhotoFragment()
        adapter.addFragment(galleryFragment!!)
        adapter.addFragment(photoFragment!!)
        viewPager.adapter = adapter

        val tabLayout: TabLayout = findViewById(R.id.tabs_bottom)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(GALLERY_FRAGMENT)?.text = getString(R.string.tag_fragment_gallery)
        tabLayout.getTabAt(PHOTO_FRAGMENT)?.text = getString(R.string.tag_fragment_photo)
    }
    companion object {
        private const val TAG = "ChoosePhotoActivity"

    }
}