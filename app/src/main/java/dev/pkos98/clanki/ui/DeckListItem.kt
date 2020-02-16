package dev.pkos98.clanki.ui

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import dev.pkos98.clanki.R
import dev.pkos98.clanki.viewmodel.DeckViewModel

public class DeckListItem(val deckVm: DeckViewModel) : AbstractItem<DeckListItem.ViewHolder>() {

    override val layoutRes: Int
        get() = R.layout.deck_list_item
    override val type: Int
        @SuppressLint("ResourceType")
        get() = R.layout.deck_list_item

    override var identifier: Long = deckVm.deckModel.id

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v, "")
    }

    class ViewHolder(view: View, test: String) : FastAdapter.ViewHolder<DeckListItem>(view) {
        var name: TextView = view.findViewById(R.id.lbl_deck_name)

        override fun bindView(item: DeckListItem, payloads: MutableList<Any>) {
            name.text = item.deckVm.deckName.value
        }

        override fun unbindView(item: DeckListItem) {
            name.text = null
        }
    }

}