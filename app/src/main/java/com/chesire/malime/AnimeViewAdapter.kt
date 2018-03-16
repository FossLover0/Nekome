package com.chesire.malime

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chesire.malime.models.Anime

class AnimeViewAdapter(
        private val items: ArrayList<Anime>
) : RecyclerView.Adapter<AnimeViewAdapter.ViewHolder>() {
    fun addAll(newItems: List<Anime>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModel(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_malmodel, parent, false))
    }

    class ViewHolder(
            private val animeView: View
    ) : RecyclerView.ViewHolder(animeView) {
        fun bindModel(animeModel: Anime) {
            val context = animeView.context
            GlideApp.with(animeView)
                    .load(animeModel.seriesImage)
                    .into(animeView.findViewById(R.id.item_malmodel_image))
            animeView.findViewById<CardView>(R.id.item_malmodel_card_view).setOnClickListener({
                CustomTabsIntent.Builder()
                        .build()
                        .launchUrl(context, Uri.parse(animeModel.malUrl))
            })
            animeView.findViewById<TextView>(R.id.item_malmodel_title).text = animeModel.seriesTitle
            animeView.findViewById<TextView>(R.id.item_malmodel_progress).text =
                    String.format(context.getString(R.string.malitem_progress_text),
                            animeModel.myWatchedEpisodes, animeModel.totalEpisodes)
        }
    }
}