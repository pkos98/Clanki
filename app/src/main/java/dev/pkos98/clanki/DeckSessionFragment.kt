package dev.pkos98.clanki

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.yuyakaido.android.cardstackview.*
import dev.pkos98.clanki.data.Card
import dev.pkos98.clanki.ui.CardListItem


public class DeckSessionFragment : Fragment(), CardStackListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deck_session, container, false)
        setupCardStackView(view)
        return view
    }

    private val itemAdapter = ItemAdapter<CardListItem>()
    private val fastAdapter = FastAdapter.with(itemAdapter)
    private var resolveState = ResolveState.Unresolved
    private lateinit var cardLayoutManager: CardStackLayoutManager

    companion object {
        const val BOTTOM_TRESHOLD = 0.4f
        const val TAG = "DeckSessionFragment"
    }

    private fun setupCardStackView(view: View) {
        cardLayoutManager = CardStackLayoutManager(requireContext(), this)
        cardLayoutManager.setDirections(listOf(Direction.Left, Direction.Right, Direction.Top))
        val cardStackView = view.findViewById<CardStackView>(R.id.csv_cards_deck)

        cardLayoutManager.setSwipeableMethod(SwipeableMethod.Manual)
        cardStackView.layoutManager = cardLayoutManager

        (1..20).forEach { i ->
            itemAdapter.add(
                CardListItem(
                    Card(
                        "**Card #$i** with nice `code` _tags_",
                        "Nice ~backtext~ with markdown support:\n * Point 1 \n * Point 2"
                    )
                )
            )
        }
        cardStackView.adapter = fastAdapter
    }

    override fun onCardDisappeared(view: View?, position: Int) { }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        if (direction == null || ratio <= BOTTOM_TRESHOLD || resolveState != ResolveState.Unresolved || direction != Direction.Bottom)
            return

        val currentCard = fastAdapter.getItem(cardLayoutManager.topPosition)!!
        toResolvedState(currentCard)
    }

    override fun onCardSwiped(direction: Direction?) { }

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {
        Log.d(TAG, "Top position: ${cardLayoutManager.topPosition}")
        toUnresolvedState()
    }

    override fun onCardRewound() {
    }

    private fun toUnresolvedState() {
        cardLayoutManager.setDirections(emptyList())
        resolveState = ResolveState.Unresolved
    }

    private fun toResolvedState(cardItem: CardListItem) {
        cardLayoutManager.setDirections(listOf(Direction.Left, Direction.Top, Direction.Right))
        cardItem.resolve()
        resolveState = ResolveState.Resolved
    }

}

private enum class ResolveState {
    Resolved,
    Unresolved
}
