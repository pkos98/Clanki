package dev.pkos98.clanki.data.access

import com.google.gson.Gson
import dev.pkos98.clanki.data.Deck
import java.nio.charset.Charset

class JsonDeckSerializerImpl : DeckSerializer {

    override fun serializeDeck(deck: Deck): ByteArray {
        return Gson().toJson(deck).toByteArray()
    }

    override fun deserializeDeck(serializedDeck: ByteArray): Deck {
        return Gson().fromJson(serializedDeck.toString(Charset.forName("UTF-8")), Deck::class.java)
    }
}