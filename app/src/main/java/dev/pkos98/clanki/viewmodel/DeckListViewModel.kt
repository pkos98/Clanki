package dev.pkos98.clanki.viewmodel

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.pkos98.clanki.data.Deck
import dev.pkos98.clanki.data.access.DeckRepository
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
public class DeckListViewModel() : ViewModel(), Parcelable {

    public lateinit var decks: List<DeckViewModel>

}
