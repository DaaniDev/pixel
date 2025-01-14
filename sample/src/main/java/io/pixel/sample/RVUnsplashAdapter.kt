package io.pixel.sample

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.keenencharles.unsplash.models.Collection
import io.pixel.Pixel
import io.pixel.config.PixelOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_center_cropped.*

class RVUnsplashAdapter(private val list: MutableList<Collection>) :
    RecyclerView.Adapter<RVUnsplashAdapter.VH>() {

    private lateinit var recyclerView: RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_center_cropped,
                parent,
                false
            )
        )
    }

    fun addAll(list: MutableList<Collection>) {
        this.list.addAll(list)
        notifyItemRangeInserted(itemCount, list.size)
        //   recyclerView.setItemViewCacheSize(itemCount)

    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val collection = list[position]
        val url = collection.coverPhoto?.urls?.small

        url?.let { Log.d("Url position: $position", it) }

        Pixel.load(
            url = url,
            imageView = holder.iv,
            pixelOptions = PixelOptions.Builder()
                .setPlaceholderResource(R.drawable.ic_loading_android)
                // .setRequest(Request.Builder().url(url).tag("Test Image Load at $position").build())
                .setImageFormat(PixelOptions.ImageFormat.JPEG).build()
        )

// coil
        /*  val d = holder.iv.load(url)
          if (position == 1)
              d.dispose()*/

        //    Glide.with(holder.itemView).load(url).into(holder.iv)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        this.recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 3)
    }

    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init {
            itemView.setOnClickListener {
                val url = list[absoluteAdapterPosition].coverPhoto?.urls?.small
                url?.let { it1 -> Log.d("Image URL", it1) }
            }
        }
    }
}