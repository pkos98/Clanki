package dev.pkos98.clanki.service

import dev.pkos98.clanki.data.access.DeckRepository
import dev.pkos98.clanki.data.access.DeckSerializer

public class DeckProviderService(
    private val deckRepo: DeckRepository,
    private val cloudSyncService: SynchronizationService
)

