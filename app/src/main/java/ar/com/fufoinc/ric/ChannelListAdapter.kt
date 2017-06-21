package ar.com.fufoinc.ric

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_channel.view.*

class ChannelListAdapter(val channels: List<Channel>,
                         val itemClick: (Channel) -> Unit) : RecyclerView.Adapter<ChannelListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindChannel(channels[position])
    }

    override fun getItemCount(): Int  = channels.count()

    class ViewHolder(itemView: View, val itemClick: (Channel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bindChannel(channel: Channel) {
            with(channel) {
                itemView.titleView.text = name
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
