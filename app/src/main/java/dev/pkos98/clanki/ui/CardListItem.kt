package dev.pkos98.clanki.ui

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import dev.pkos98.clanki.R
import dev.pkos98.clanki.data.Card
import io.noties.markwon.Markwon

public class CardListItem(public val card: Card) : AbstractItem<CardListItem.ViewHolder>() {

    private var viewHolderCache: ViewHolder? = null

    private var isResolved: Boolean = false
        get() {
            if (viewHolderCache == null)
                return false
            return viewHolderCache!!.cardBackTextView.isVisible
        }
        private set

    override val layoutRes: Int
        get() = R.layout.card_list_item
    override val type: Int
        get() = card.frontText.hashCode()

    override fun getViewHolder(v: View): ViewHolder {
        viewHolderCache = ViewHolder(v)
        return viewHolderCache!!
    }

    public fun resolve() {
        if (isResolved || viewHolderCache == null)
            return

        isResolved = true
        viewHolderCache!!.cardBackTextView.isVisible = true
    }

    public class ViewHolder(view: View) : FastAdapter.ViewHolder<CardListItem>(view) {

        private val markdownFormatter = Markwon.create(view.context)
        private val cardFrontTextView: TextView = view.findViewById(R.id.lbl_card_front)
        internal val cardBackTextView: TextView = view.findViewById(R.id.lbl_card_back)

        override fun bindView(item: CardListItem, payloads: MutableList<Any>) {

            cardFrontTextView.text = markdownFormatter.toMarkdown(item.card.frontText)
            cardBackTextView.text = markdownFormatter.toMarkdown(item.card.backText)
        }

        override fun unbindView(item: CardListItem) {
            cardFrontTextView.text = null
            cardBackTextView.text = null
        }
    }
}