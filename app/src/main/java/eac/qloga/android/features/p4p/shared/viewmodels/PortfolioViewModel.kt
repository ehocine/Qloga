package eac.qloga.android.features.p4p.shared.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.PortfolioImage
import eac.qloga.android.features.p4p.showroom.shared.utils.PortfolioEvent
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PortfolioViewModel @Inject constructor(): ViewModel(){

    companion object{
        const val TAG = "PortfolioViewModel"
    }

    // full text means all the description of image are shown
    var showFullTextDetails by mutableStateOf(false)
        private set

    var images by mutableStateOf<List<PortfolioImage>>(emptyList())
        private set

    // current image in full mode showing
    var currentImageDisplay by mutableStateOf<PortfolioImage?>(null)
        private set

    init {
        getPortfolioImages()
        setCurrentDisplayImage()
    }

    fun onTriggerEvent(event: PortfolioEvent){
         try {
            viewModelScope.launch {
              when(event){
                  is PortfolioEvent.SelectImage -> {}
                  else -> {}
              }
            }
         }catch (e: Exception){
             Log.e(TAG, "onTriggerEvent: ${e.printStackTrace()}")
         }
    }


    fun onClickInfo(){
        showFullTextDetails = !showFullTextDetails
    }

    fun onChangeImage(portfolioImage: PortfolioImage){
        currentImageDisplay= portfolioImage
    }

    private fun setCurrentDisplayImage(){
        if(images.isNotEmpty()){
            try {
                currentImageDisplay = images[0]
            }catch (e: Exception){
                Log.e(TAG, "setCurrentDisplayImage: ${e.printStackTrace()}")
            }
        }
    }

    fun drawableToBitmap(context: Context, id: Int): ImageBitmap {
        return BitmapFactory.decodeResource( context.resources, id).asImageBitmap()
    }

    fun bitMapToDrawable(context: Context, bitmap: Bitmap): BitmapDrawable{
        return BitmapDrawable(context.resources, bitmap)
    }

    private fun getPortfolioImages(){
        /**
         *  Simulating the data , later it will be fetched from api
         * **/
        images = listOf(
            PortfolioImage(
                id = 0,
                link = R.drawable.arch1,
                description = ("Internal and external drain and sewer repair, including " +
                        "unblocking and cleaning and replacing drains").repeat(3)
            ),
            PortfolioImage(
                id = 1,
                link = R.drawable.arch2,
                description = ("Internal and external drain and sewer repair, including " +
                        "unblocking and cleaning and replacing drains").repeat(3)
            ),
            PortfolioImage(
                id = 2,
                link = R.drawable.arch3,
                description = ("Internal and external drain and sewer repair, including" +
                        " unblocking and cleaning and replacing drains").repeat(3)
            ),
            PortfolioImage(
                id = 3,
                link = R.drawable.arch4,
                description = ("Internal and external drain and sewer repair, including" +
                        " unblocking and cleaning and replacing drains").repeat(3)
            ),
            PortfolioImage(
                id = 4,
                link = R.drawable.arch4,
                description = ("Internal and external drain and sewer repair, including" +
                        " unblocking and cleaning and replacing drains").repeat(3)
            ),
            PortfolioImage(
                id = 5,
                link = R.drawable.arch5,
                description = ("Internal and external drain and sewer repair, including" +
                        " unblocking and cleaning and replacing drains").repeat(3)
            ),
            PortfolioImage(
                id = 6,
                link = R.drawable.arch6,
                description = ("Internal and external drain and sewer repair, including " +
                        "unblocking and cleaning and replacing drains").repeat(3)
            ),
            PortfolioImage(
                id = 7,
                link = R.drawable.home_background,
                description = ("Internal and external drain and sewer repair, including" +
                        " unblocking and cleaning and replacing drains").repeat(3)
            ),
            PortfolioImage(
                id = 8,
                link = R.drawable.model_profile,
                description = ("Internal and external drain and sewer repair, including " +
                        "unblocking and cleaning and replacing drains").repeat(3)
            )
        )
    }
}


