package dev.pkos98.clanki.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import dev.pkos98.clanki.R
import dev.pkos98.clanki.viewmodel.DeckViewModel


public class DeckDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var deckVm: DeckViewModel
    private var deckNameEditText: TextInputEditText? = null
    private var newDeckName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deck_details_bottom_sheet, container, false)

        deckVm = arguments?.get("deckViewModel") as DeckViewModel

        val deleteButton = view.findViewById<Button>(R.id.btn_delete_deck)
        deleteButton.setOnClickListener {
            deckVm.shallDelete.value = true
            deleteButton.isEnabled = false
            this.dialog?.cancel()
        }

        deckNameEditText = view.findViewById(R.id.edt_deck_name)
        deckNameEditText?.setText(deckVm.deckName.value)

        deckNameEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                newDeckName = s!!.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        return view
    }

    override fun onStop() {
        super.onStop()

        if (newDeckName.isNotEmpty() && !deckVm.shallDelete.value!!) {
            deckVm.deckName.value = newDeckName
        }
        deckNameEditText = null
    }
}