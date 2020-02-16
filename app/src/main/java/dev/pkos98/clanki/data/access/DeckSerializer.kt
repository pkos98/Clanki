package dev.pkos98.clanki.data.access

import dev.pkos98.clanki.data.Deck

public interface DeckSerializer {
    fun serializeDeck(deck: Deck): ByteArray
    fun deserializeDeck(serializedDeck: ByteArray): Deck
}