package dev.pkos98.clanki.data.access

import android.content.Context
import dev.pkos98.clanki.data.Deck
import dev.pkos98.clanki.exception.DecknameDuplicateException
import java.io.File

public class LocalDeckRepositoryImpl(
    private val context: Context,
    private val deckSerializer: DeckSerializer
) : DeckRepository {

    private var deckListCache: MutableList<Deck> = mutableListOf()

    override fun getAllDecks(): List<Deck> {
        if (deckListCache.isEmpty())
            deckListCache = getAllDecks(context.filesDir.listFiles().toList()).toMutableList()
        return deckListCache
    }

    override fun getDeckByName(deckName: String): Deck {
        return getAllDecks().first { d -> d.name == deckName }
    }

    override fun addDeck(deck: Deck) {
        if (getAllDecks().any { d -> d.id == deck.id })
            throw DecknameDuplicateException(deck.name)
        val newDeckFile = File(context.filesDir, deck.id.toString())
        newDeckFile.writeBytes(deckSerializer.serializeDeck(deck))
        deckListCache.add(deck)
    }

    override fun removeDeckByName(deckName: String) {
        val fileToRemove = getDeckFileById(getDeckByName(deckName).id)
        fileToRemove.delete()
        deckListCache.remove(getDeckByName(deckName))
    }

    override fun updateDeck(deck: Deck): Deck {
        val fileToUpdate = getDeckFileById(deck.id)
        val serializedDeck = deckSerializer.serializeDeck(deck)
        fileToUpdate.writeBytes(serializedDeck)
        deckListCache[deckListCache.indexOf(getAllDecks().first { t -> t.id == deck.id })] = deck
        return deck
    }

    // functional core
    private fun getAllDecks(files: List<File>): List<Deck> {
        return files.map { f -> deckSerializer.deserializeDeck(f.readBytes()) }
    }

    private fun getDeckFileById(id: Long): File {
        return context.filesDir.listFiles().first { f -> f.name == id.toString() }
    }

}
