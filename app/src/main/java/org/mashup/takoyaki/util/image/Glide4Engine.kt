package org.mashup.takoyaki.util.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.zhihu.matisse.engine.ImageEngine
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.gif.GifDrawable


/**
 * Created by whdcn on 2018-07-25.
 */
class Glide4Engine : ImageEngine {
    override fun loadImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri) {
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions()
                               .override(resizeX, resizeY)
                               .priority(Priority.HIGH)
                               .fitCenter())
                .into(imageView)
    }

    override fun loadGifImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri) {
        Glide.with(context)
                .asGif()
                .load(uri)
                .apply(RequestOptions()
                               .override(resizeX, resizeY)
                               .priority(Priority.HIGH)
                               .fitCenter())
                .into(imageView)
    }

    override fun supportAnimatedGif(): Boolean {
        return true
    }

    override fun loadGifThumbnail(context: Context, resize: Int, placeholder: Drawable, imageView: ImageView, uri: Uri) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(RequestOptions()
                               .override(resize, resize)
                               .placeholder(placeholder)
                               .centerCrop())
                .into(imageView)
    }

    override fun loadThumbnail(context: Context, resize: Int, placeholder: Drawable, imageView: ImageView, uri: Uri) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(RequestOptions()
                               .override(resize, resize)
                               .placeholder(placeholder)
                               .centerCrop())
                .into(imageView)
    }
}