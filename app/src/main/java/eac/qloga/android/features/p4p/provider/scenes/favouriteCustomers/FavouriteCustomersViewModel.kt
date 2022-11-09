package eac.qloga.android.features.p4p.provider.scenes.favouriteCustomers

import android.app.Application
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.MediaRepository
import eac.qloga.android.data.shared.models.AvatarImage
import eac.qloga.p4p.cst.dto.CstPublicProfile
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FavouriteCustomersViewModel @Inject constructor(
    application: Application,
    private val p4pProviderRepository: P4pProviderRepository,
    private val mediaRepository: MediaRepository
): AndroidViewModel(application) {

    companion object {
        const val TAG = "FavCstViewModel"
    }

    private val _cstPublicProfileList = mutableStateOf<List<CstPublicProfile>>(emptyList())
    val cstPublicProfileList: State<List<CstPublicProfile>> = _cstPublicProfileList

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    private val _providerId = mutableStateOf<Long?>(1001)
    val providerId: State<Long?> = _providerId

    private val _cstPublicProfileState = MutableStateFlow(LoadingState.IDLE)
    val cstPublicProfileState = _cstPublicProfileState.asStateFlow()

    private val _avatarList = mutableStateOf<List<AvatarImage>>(emptyList())
    val avatarList: State<List<AvatarImage>> = _avatarList

    init {
        preCallsLoad()
    }

    fun preCallsLoad(){
        loadFavouriteCustomers()
    }

    fun loadFavouriteCustomers(){
        viewModelScope.launch( coroutineExceptionHandler) {
            try {
                _cstPublicProfileState.emit(LoadingState.LOADING)
                _providerId.value?.let { id ->
                    val response = p4pProviderRepository.getFavCustomers(prvId = id)
                    _cstPublicProfileList.value = response.sortedBy { it.fullName }
                    _cstPublicProfileState.emit(LoadingState.LOADED)
                    response.forEach {
                        getFavCstProfileImage(it.avatarId)
                    }
                }
                _cstPublicProfileState.emit(LoadingState.LOADED)
            }catch (e: IOException){
                _cstPublicProfileState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    fun getFavCstProfileImage(mediaId: Long) {

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
//                val response = mediaRepository.getImageDataUrl(mediaId,null)
//                if(response.isSuccessful){
//                    val bitmap = BitmapFactory.decodeStream(response.body()?.byteStream())
//                    val avatarListData = ArrayList(_avatarList.value)
//                    avatarListData.add(AvatarImage(id = mediaId, bitmap = bitmap))
//                    _avatarList.value = avatarListData
//                }else{
//                    Log.e(TAG, "getFavCstProfileImage: code = ${response.code()}, err = ${response.errorBody()}")
//                }
            }catch (e: IOException){
                e.printStackTrace()
                Log.e(TAG, "loadFavouriteCustomersMedia: ${e.cause}")
            }
        }
    }
}