package dev.pkos98.clanki.data.access

import dev.pkos98.clanki.data.Deck

public interface DeckRepository {

    fun getAllDecks(): List<Deck>

    fun getDeckByName(deckName: String): Deck

    fun addDeck(deck: Deck)

    fun removeDeckByName(deckName: String)

    fun updateDeck(deck: Deck): Deck

}
