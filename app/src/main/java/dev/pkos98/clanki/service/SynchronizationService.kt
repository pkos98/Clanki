package dev.pkos98.clanki.service

import dev.pkos98.clanki.data.Deck
import java.util.*

public interface SynchronizationService {

    /**
     * Returns the latest time when the sync provider was updated (written to)
     */
    var lastSync: Date

    fun UpdateDeck(deck: Deck)

}
