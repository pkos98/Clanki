package dev.pkos98.clanki.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

@Parcelize
public data class Deck(public val id: Long, public var name: String, public var lastWrite: Date, public var cards: @RawValue List<Card> = emptyList()): Parcelable {

}
