package eac.qloga.android.features.p4p.shared.scenes.ratingDetails

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.p4p.core.dto.Rating
import javax.inject.Inject

@HiltViewModel
class RatingDetailsViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application) {

    companion object{
        val rating = mutableStateOf<Float?>(null)
        val ratings = mutableStateOf<List<Rating>>(emptyList())
    }
}