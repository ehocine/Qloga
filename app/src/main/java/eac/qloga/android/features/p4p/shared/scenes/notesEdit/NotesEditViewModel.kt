package eac.qloga.android.features.p4p.shared.scenes.notesEdit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.InputFieldState
import javax.inject.Inject

@HiltViewModel
class NotesEditViewModel @Inject constructor(): ViewModel() {

    private val _notes = mutableStateOf(InputFieldState(hint = "Your notes..."))
    val notes: State<InputFieldState> = _notes

    fun onEnterNotes(value: String){
        _notes.value = _notes.value.copy(
            text = value
        )
    }

    fun onFocusNotes(focusState: FocusState){
        _notes.value = _notes.value.copy(
            isFocused = focusState.isFocused
        )
    }
}