package eac.qloga.android.features.p4p.shared.scenes.reviews

import android.app.Application
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.data.p4p.customer.P4pCustomerRepository
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.MediaRepository
import eac.qloga.android.data.shared.models.AvatarImage
import eac.qloga.bare.enums.AvatarType
import eac.qloga.p4p.order.dto.OrderReview
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    application: Application,
    private val mediaRepository: MediaRepository,
): AndroidViewModel(application) {

    companion object{
        const val TAG = "ReviewsViewModel"
        val reviews = mutableStateOf<List<OrderReview>>(emptyList())
    }

    private val _avatarList = mutableStateOf<List<AvatarImage>>(emptyList())
    val avatarList: State<List<AvatarImage>> = _avatarList

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    fun loadAvatar(){
        reviews.value.forEach { review ->
            review.reviewerId?.let {
                getAvatar(it)
            }
        }
    }

    private fun getAvatar(id: Long) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                val response = mediaRepository.getAvatar(otype = AvatarType.PERSON, id = id)
                val bitmap = BitmapFactory.decodeStream(response.byteStream())
                val avatarListClone = ArrayList(_avatarList.value)
                avatarListClone.add(AvatarImage(id = id, bitmap = bitmap))
                _avatarList.value = avatarListClone
            }catch (e: IOException){
                e.printStackTrace()
                Log.e(TAG, "getAvatar: ${e.printStackTrace()}")
            }
        }
    }
}