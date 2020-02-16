package dev.pkos98.clanki

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import dev.pkos98.clanki.data.Card
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
public class CardViewModel(val cardModel: @RawValue Card) : ViewModel(), Parcelable {

    public var isResolved = false
    public var frontText
        get() = cardModel.frontText
        set(value) {
            cardModel.frontText = value
        }
    public var backText
        get() = cardModel.backText
        set(value) {
            cardModel.backText = value
        }

}
