package dev.pkos98.clanki.viewmodel

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.pkos98.clanki.data.Deck
import kotlinx.android.parcel.Parcelize

@Parcelize
public class DeckViewModel(val deckModel: Deck) : ViewModel(), Parcelable {

    public var deckName: MutableLiveData<String> = MutableLiveData(deckModel.name)
    public var shallDelete: MutableLiveData<Boolean> = MutableLiveData(false)
    public var isChanged = false

    init {
        deckName.observeForever {
            if (deckName.value != deckModel.name) {
                deckModel.name = it
                isChanged = true
            }
        }
        shallDelete.observeForever {
            if (shallDelete.value == true) {
                isChanged = true
            }
        }
    }

}
