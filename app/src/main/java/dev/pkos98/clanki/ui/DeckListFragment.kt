package dev.pkos98.clanki.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.ISelectionListener
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.select.getSelectExtension
import dev.pkos98.clanki.R
import dev.pkos98.clanki.data.Deck
import dev.pkos98.clanki.data.access.DeckRepository
import dev.pkos98.clanki.data.access.JsonDeckSerializerImpl
import dev.pkos98.clanki.data.access.LocalDeckRepositoryImpl
import dev.pkos98.clanki.utils.navigate
import dev.pkos98.clanki.viewmodel.DeckListViewModel
import dev.pkos98.clanki.viewmodel.DeckViewModel
import java.util.*


public class DeckListFragment : Fragment() {

    @IdRes
    private val destinationId: Int = R.id.dest_deck_list_fragment
    private lateinit var deckRepo: DeckRepository
    private val TAG = "DeckListFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deck_list, container, false)

        deckRepo = createRepo(view.context)
        if (deckRepo.getAllDecks().isEmpty()) {
            (1..20).forEach { i -> deckRepo.addDeck(Deck(System.currentTimeMillis(), "Deck #$i", Date())) }
        }

        val deckListVm: DeckListViewModel by viewModels()
        deckListVm.decks = deckRepo.getAllDecks().map { d -> DeckViewModel(d) } as MutableList<DeckViewModel>
        setupDeckList(view, deckListVm.decks)

        view.findViewById<FloatingActionButton>(R.id.fab_create_deck).setOnClickListener {
            onAddDeckButtonClicked()
        }

        return view
    }

    private fun setupDeckList(view: View, decks: List<DeckViewModel>) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.lst_decks)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val itemAdapter = ItemAdapter<DeckListItem>()
        val fastAdapter = FastAdapter.with(itemAdapter)

        //set our adapters to the RecyclerView
        recyclerView.adapter = fastAdapter

        decks.map { d -> itemAdapter.add(DeckListItem(DeckViewModel(d.deckModel))) }
        recyclerView.adapter = fastAdapter

        // enable selection
        // gets (or creates and attaches if not yet existing) the extension from the given `FastAdapter`
        val selectExtension = fastAdapter.getSelectExtension()
        selectExtension.apply {
            isSelectable = true
            multiSelect = false
            selectOnLongClick = true
            selectionListener = object : ISelectionListener<DeckListItem> {
                override fun onSelectionChanged(item: DeckListItem, selected: Boolean) {

                    if (!selected)
                        return

                    item.deckVm.deckName.removeObservers(viewLifecycleOwner)
                    item.deckVm.shallDelete.removeObservers(viewLifecycleOwner)
                    item.deckVm.deckName.observe(viewLifecycleOwner, Observer<String> {
                        onDeckRenamed(item, fastAdapter)
                    })
                    item.deckVm.shallDelete.observe(viewLifecycleOwner, Observer<Boolean> {
                        onDeleteDeck(item, deckRepo, fastAdapter, itemAdapter)
                    })
                    val selectedDeck = item.deckVm
                    DeckListFragmentDirections.actionOpenDeckListBottomSheet(selectedDeck)
                        .navigate(destinationId, view)
                }
            }
        }


        fastAdapter.onClickListener =
            { _, _, item, _ ->
                DeckListFragmentDirections.actionOpenDeckSession(item.deckVm)
                    .navigate(destinationId, view)
                true
            }

    }

    private fun createRepo(appContext: Context): DeckRepository {
        val serializer = JsonDeckSerializerImpl()
        return LocalDeckRepositoryImpl(appContext.applicationContext, serializer)
    }

    private fun onDeckRenamed(item: DeckListItem, adapter: FastAdapter<DeckListItem>) {
        Log.d(TAG, "Renamed called")
        if (!item.deckVm.isChanged || item.deckVm.shallDelete.value!!)
            return

        deckRepo.updateDeck(item.deckVm.deckModel)
        item.deckVm.isChanged = false
        val pos = adapter.getPosition(item.identifier)
        adapter.notifyAdapterItemChanged(pos)
    }

    private fun onDeleteDeck(
        item: DeckListItem, deckRepo: DeckRepository,
        fastAdapter: FastAdapter<DeckListItem>, itemAdapter: ItemAdapter<DeckListItem>
    ) {
        if (!item.deckVm.shallDelete.value!! || !itemAdapter.itemList.items.contains(item))
            return
        removeDeck(item, itemAdapter, fastAdapter, deckRepo)
    }

    private fun onAddDeckButtonClicked() {
        DeckListFragmentDirections
            .actionOpenDeckDetails(DeckViewModel(Deck(System.currentTimeMillis(), "A new deck", Date())))
            .navigate(destinationId, view!!)

    }

    private fun removeDeck(
        item: DeckListItem,
        itemAdapter: ItemAdapter<DeckListItem>,
        fastAdapter: FastAdapter<DeckListItem>,
        deckRepo: DeckRepository
    ) {
        itemAdapter.adapterItems.remove(item)
        deckRepo.removeDeckByName(item.deckVm.deckModel.name)
        fastAdapter.notifyAdapterItemRemoved(fastAdapter.getPosition(item))
    }


}

