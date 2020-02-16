package dev.pkos98.clanki.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.pkos98.clanki.R
import dev.pkos98.clanki.viewmodel.DeckViewModel

public class DeckViewModelAdapter(decks: ArrayList<DeckViewModel>): RecyclerView.Adapter<DeckViewModelAdapter.DeckViewHolder>() {

    class DeckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.deck_list_item, parent, false)
        return DeckViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}