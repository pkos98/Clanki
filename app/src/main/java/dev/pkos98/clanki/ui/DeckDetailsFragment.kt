package dev.pkos98.clanki.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import dev.pkos98.clanki.R
import dev.pkos98.clanki.viewmodel.DeckViewModel

class DeckDetailsFragment : Fragment() {

   override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view =inflater.inflate(R.layout.fragment_deck_details, container, false)

       val deckVm = arguments?.get("deckViewModel") as DeckViewModel
       val deckNameEditText = view.findViewById<TextInputEditText>(R.id.edt_details_deck_name);
       deckNameEditText.setText(deckVm.deckName.value!!)
       return view
    }

}