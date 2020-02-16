package dev.pkos98.clanki.ui

import android.view.View

public interface DeckListBottomSheetFragmentListener {
    fun onRenameTextEdited(newText: String)
    fun onDeleteDeckClicked()
}