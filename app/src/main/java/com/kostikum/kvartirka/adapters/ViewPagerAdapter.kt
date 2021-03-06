package com.kostikum.kvartirka.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewpager.widget.PagerAdapter
import com.kostikum.kvartirka.databinding.PagerItemBinding
import com.kostikum.kvartirka.entity.Photo
import javax.inject.Inject

class ViewPagerAdapter
    @Inject constructor() : PagerAdapter() {
    private var photos: List<Photo> = emptyList()

    fun setPhotos(photosList: List<Photo>) {
        photos = photosList
    }
    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = photos.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return PagerItemBinding.inflate(
            LayoutInflater.from(container.context),
            container, false
        ).apply {
            photo = photos[position]
            executePendingBindings()
            container.addView(root)
        }.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as FrameLayout)
    }
}
