package com.target.targetcasestudy.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.target.targetcasestudy.R
import kotlinx.android.synthetic.main.network_image.view.*

/**
 * Data class for specifying Glide params
 */
data class GlideParams(
    var scaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_CENTER, // ImageView scale type
    var errorImage: Int = R.drawable.glide_error, // Drawable resource to be shown in case of an error
    var priority: Priority = Priority.NORMAL, // Priorities for completing loads.
    var timeout: Int = NetworkImageView.GLIDE_TIMEOUT, // Timeout for http requests Defaults to 2.5 secs in Glide which seems to be too less
    var skipMemCache: Boolean = false, // Allows the loaded resource to skip the memory cache
    var diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC, // Disk caching strategies
    var overrideSize: Int = -1 // This is the default value used in RequestOptions class(RequestOptions.UNSET) for override height and width
)

interface ImageLoaderCallback {
    /**
     * Indicates a successful image load
     * @param view imageView on which the image is loaded
     */
    fun onImageLoadSuccess(view: View, url: String)
    /**
     * Indicates an error in image load
     * @param glideException Exception thrown from Glide library
     */
    fun onImageLoadError(glideException: GlideException?)
}

class NetworkImageView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    private var progress : ProgressBar? = null
    private var isLightTheme = true
    private var showProgressRunnable = Runnable {
        if (progress == null) {
            progress = ProgressBar(getContext())
            val lp = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER)
            progress?.layoutParams = lp
            addView(progress, 0)
        }
    }

    private var hideProgressRunnable: Runnable = Runnable {
        removeView(progress)
        progress = null
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.network_image, this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NetworkImageView)
        isLightTheme = typedArray.getBoolean(R.styleable.NetworkImageView_isLightNetworkView, isLightTheme)
        if (typedArray.getBoolean(R.styleable.NetworkImageView_showProgressOnDisplay, false)){
            showProgress()
        }
        typedArray.recycle()
    }

    /**
     * Invokes ImageView extension function to start downloading the image from the specified defaultURL
     * @param url resource from which image needs to be downloaded
     * @param glideParams (Optional) parameter to specify Glide library parameters
     * @param imageLoaderCallback (Optional) callback to be invoked on success or error while loading
     */
    fun setImageUrl(url: String, glideParams: GlideParams = GlideParams(), imageLoaderCallback: ImageLoaderCallback? = null) {
        showProgress()
        networkImage.setImageUrl(url,glideParams,object : ImageLoaderCallback {
            override fun onImageLoadSuccess(view: View, url: String) {
                // set visibility and pass the success to the caller
                hideProgress()
                imageLoaderCallback?.onImageLoadSuccess(view,url)
            }
            override fun onImageLoadError(glideException: GlideException?) {
                // set visibility and pass the error to caller
                hideProgress()
                imageLoaderCallback?.onImageLoadError(glideException)
            }
        })
    }

    private fun showProgress() {
        post(showProgressRunnable)
    }

    private fun hideProgress() {
        post(hideProgressRunnable)
    }

    companion object {
        const val GLIDE_TIMEOUT = 10000
    }
}

/**
 * Extension function to load image from an URL using Glide library
 * @param url resource from which image needs to be downloaded
 * @param glideParams (Optional) parameter to specify Glide library parameters
 * @param imageLoaderCallback (Optional) callback to be invoked on success or error while loading
 */
fun ImageView.setImageUrl(url: String, glideParams: GlideParams = GlideParams(), imageLoaderCallback: ImageLoaderCallback? = null) {
    this.scaleType = glideParams.scaleType
    val requestOptions = RequestOptions.
    diskCacheStrategyOf(glideParams.diskCacheStrategy).
    error(glideParams.errorImage).
    skipMemoryCache(glideParams.skipMemCache).
    priority(glideParams.priority).
    timeout(glideParams.timeout).
    override(glideParams.overrideSize)

    Glide.with(this).
    load(url).
    apply(requestOptions).
    listener(object : RequestListener<Drawable> {
        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            imageLoaderCallback?.onImageLoadSuccess(this@setImageUrl, url)
            return false
        }

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            imageLoaderCallback?.onImageLoadError(e)
            return false
        }
    }).
    into(this)
}
