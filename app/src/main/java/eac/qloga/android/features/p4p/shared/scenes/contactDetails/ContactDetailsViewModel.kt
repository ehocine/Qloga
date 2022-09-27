package eac.qloga.android.features.p4p.shared.scenes.contactDetails

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.bare.dto.Contacts
import eac.qloga.p4p.core.dto.Rating
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application) {

    companion object{
        val contacts = mutableStateOf<Contacts?>(null)
    }
}