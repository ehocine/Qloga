package eac.qloga.android.features.p4p.shared.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.showroom.shared.utils.MediaEvent
import eac.qloga.bare.dto.media.MediaAlbum
import eac.qloga.bare.dto.media.MediaMeta
import eac.qloga.bare.enums.AccessLevel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(): ViewModel(){

    private val _mediaImages = mutableStateOf<List<MediaMeta>>(emptyList())
    val mediaImages: State<List<MediaMeta>> = _mediaImages

    private val _imageNameInputState = mutableStateOf(InputFieldState(hint = "Enter image name"))
    val imageNameInputState: State<InputFieldState> = _imageNameInputState

    private val _imageDescInputState = mutableStateOf(InputFieldState(hint = "Description(optional)"))
    val imageDescInputState: State<InputFieldState> = _imageDescInputState

    private val _mediaFolderNameInputState = mutableStateOf(InputFieldState(hint = "Enter album name"))
    val mediaFolderNameInputState: State<InputFieldState> = _mediaFolderNameInputState

    private val _mediaFolderDescInputState = mutableStateOf(InputFieldState(hint = "Description(optional)"))
    val mediaFolderDescInputState: State<InputFieldState> = _mediaFolderDescInputState

    private val _mediaFolderAccessLevel = mutableStateOf<AccessLevel>(AccessLevel.PRIVATE)
    val mediaFolderAccessLevel: State<AccessLevel> = _mediaFolderAccessLevel

    private val _imageAccessLevel = mutableStateOf<AccessLevel>(AccessLevel.PRIVATE)
    val imageAccessLevel: State<AccessLevel> = _imageAccessLevel

    private val _isImageSelectable = mutableStateOf(false)
    val isImageSelectable: State<Boolean> = _isImageSelectable

    private val _selectedImages = mutableStateOf<List<MediaMeta>>(emptyList())
    val selectedImages: State<List<MediaMeta>> = _selectedImages

    private val _fullViewImage = mutableStateOf<MediaMeta?>(null)
    val fullViewImage: State<MediaMeta?> = _fullViewImage

    private val _selectedFolder = mutableStateOf<MediaAlbum?>(null)
    val selectedFolder: State<MediaAlbum?> = _selectedFolder

    private val _currentFolder = mutableStateOf<MediaAlbum?>(null)
    val currentFolder: State<MediaAlbum?> = _currentFolder

    private val _folderToBeAdded = mutableStateOf<MediaAlbum?>(null)
    val folderToBeAdded: State<MediaAlbum?> = _folderToBeAdded

    private val _folders = mutableStateOf<List<MediaAlbum>>(emptyList())
    val folders: State<List<MediaAlbum>> = _folders

    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: State<Uri?> = _imageUri

    private val _bitmap = mutableStateOf<Bitmap?>(null)
    val bitmap: State<Bitmap?> = _bitmap

    private val _showDeleteDialog = mutableStateOf(false)
    val showDeleteDialog: State<Boolean> = _showDeleteDialog

    init {
        getMedias()
        setFullViewImage(id = 34)
        setStateEditImage()
        setStateEditMediaFolder()
    }

    fun onTriggerEvent(event: MediaEvent){
         try {
            viewModelScope.launch {
              when(event){
                  is MediaEvent.LongPressImage -> { _isImageSelectable.value = !isImageSelectable.value }
                  is MediaEvent.SelectImage -> { onSelectImages(event.images) }
                  is MediaEvent.SelectAllImages -> { onSelectAllImages() }
                  is MediaEvent.EnterImageName -> { onImageName(event.name) }
                  is MediaEvent.EnterMediaFolderName -> { onMediaFolderName(event.name) }
                  is MediaEvent.EnterImageDesc -> { onImageDesc(event.desc) }
                  is MediaEvent.EnterMediaFolderDesc -> { onMediaFolderDesc(event.desc) }
                  is MediaEvent.FocusImageName -> { onFocusImageName(event.focusState) }
                  is MediaEvent.FocusMediaFolderName -> { onFocusMediaFolderName(event.focusState) }
                  is MediaEvent.FocusImageDesc -> { onFocusImageDesc(event.focusState) }
                  is MediaEvent.FocusMediaFolderDesc -> { onFocusMediaFolderDesc(event.focusState) }
                  is MediaEvent.ChangeMediaFolderAccessLevel -> { onChangeMediaFolderAccessLevel(event.access) }
                  is MediaEvent.ChangeImageAccessLevel -> { onChangeImageAccessLevel(event.access) }
                  is MediaEvent.DeleteImage -> { onDeleteImage() }
                  is MediaEvent.CloseSelectable -> { onCloseSelectable() }
                  is MediaEvent.UpdateImage -> {  }
                  is MediaEvent.UpdateMediaFolder -> { onUpdateMediaFolder()}
                  is MediaEvent.SelectFolderToMove -> { onSelectFolderToMove(event.folder) }
                  is MediaEvent.Move -> { onMoveAnotherFolder()}
              }
            }
         }catch (e: Exception){
             Log.e("TAG", "onTriggerEvent: ${e.printStackTrace()}")
         }
    }

    private fun onMoveAnotherFolder() {
        //TODO("Not yet implemented")
    }

    private fun onUpdateMediaFolder() {
//        _currentFolder.value = _currentFolder.value?.copy(
//            name = _mediaFolderNameInputState.value.text,
//            description = _mediaFolderDescInputState.value.text,
//            access = _mediaFolderAccessLevel.value.label
//        )
    }

    private fun onSelectFolderToMove(folder: MediaAlbum) {
        _selectedFolder.value = folder
        Log.d(TAG, "onSelectFolderToMove: ${folder.name}")
    }

    private fun onCloseSelectable() {
        _isImageSelectable.value = false
        clearSelectedImages()
    }

    private fun clearSelectedImages(){
        _selectedImages.value = emptyList()
    }

    fun setFolderToBeAdded(folder: MediaAlbum){
        _folderToBeAdded.value = folder
    }

    private fun onDeleteImage() {
        // later delete into server
        // removing all images which are selected
        val allImages = ArrayList<MediaMeta>(_mediaImages.value)
        allImages.removeAll(selectedImages.value.toSet())
        _mediaImages.value = allImages
        clearSelectedImages()
    }

    private fun onFocusImageName(focusState: FocusState) {
        _imageNameInputState.value = _imageNameInputState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onFocusMediaFolderName(focusState: FocusState) {
        _mediaFolderNameInputState.value = _mediaFolderNameInputState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onFocusImageDesc(focusState: FocusState) {
        _imageDescInputState.value = _imageDescInputState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onFocusMediaFolderDesc(focusState: FocusState) {
        _mediaFolderDescInputState.value = _mediaFolderDescInputState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onChangeMediaFolderAccessLevel(access: AccessLevel) {
        _mediaFolderAccessLevel.value = access
    }

    private fun onChangeImageAccessLevel(access: AccessLevel) {
        _imageAccessLevel.value = access
    }

    private fun onImageDesc(desc: String) {
        _imageDescInputState.value = _imageDescInputState.value.copy(
            text = desc
        )
    }

    private fun onMediaFolderDesc(desc: String) {
        _mediaFolderDescInputState.value = _mediaFolderDescInputState.value.copy(
            text = desc
        )
    }

    private fun onImageName(name: String) {
        _imageNameInputState.value = _imageNameInputState.value.copy(
            text = name
        )
        Log.d(TAG, "onImageName: ${imageNameInputState.value.text} ")
    }

    private fun onMediaFolderName(name: String){
        _mediaFolderNameInputState.value = _mediaFolderNameInputState.value.copy(
            text = name
        )
    }

    fun onShowDeleteDialog(value: Boolean){
        _showDeleteDialog.value = value
    }

    private fun onSelectImages(images: List<MediaMeta>){
        val newImages = ArrayList(this._selectedImages.value)
        images.forEach { image ->
            if(image in newImages){
                //remove if image is already selected
                newImages.remove(image)
            }else{
                //add if image is not selected already
                newImages.add(image)
            }
        }
        _selectedImages.value = newImages
    }

    /**
     *  If the size selected is more than media size
     *  that means all images are already selected, so remove
     *  all selection else select all images
     * */
    private fun onSelectAllImages(){
        val newImages = ArrayList(this._selectedImages.value)
        if(_selectedImages.value.size >= _mediaImages.value.size){
            newImages.clear()
        }else{
            newImages.addAll(_mediaImages.value)
        }
        _selectedImages.value = newImages
    }

    fun initializeNavArguments(albumId: String){
        //TODO init the arguments
    }

    fun onSetCurrentFolder(album: MediaAlbum){
        _currentFolder.value = album
    }

    private fun setFullViewImage(id : Long){
        val imageId = id
//        _fullViewImage.value = MediaMeta(
//            id = R.drawable.arch6.toLong(),
//            filename = "kitchen.png",
//            summery = "This is summery",
//            contentType = "",
//            height = 34,
//            width = 34,
//            size =34,
//            uploadedDate =  353535,
//            uploaderId = 234,
//            accessLevel = "Private"
//        )
    }

    fun setStateEditImage(){
        _imageNameInputState.value = _imageNameInputState.value.copy(
            text = _fullViewImage.value?.filename ?: ""
        )
//        _imageDescInputState.value = _imageDescInputState.value.copy(
//            text = _fullViewImage.value?.summery ?: ""
//        )
//        _imageAccessLevel.value = when(_fullViewImage.value?.accessLevel ?: ""){
//            AccessLevel.PRIVATE.label -> AccessLevel.Private
//            AccessLevel.PUBLIC.label -> AccessLevel.Public
//            AccessLevel.Shared.label -> AccessLevel.Shared
//            else -> AccessLevel.Private
//        }
    }

    fun setStateEditMediaFolder(){
        Log.d(TAG, "setStateEditMediaFolder: ${_currentFolder.value}")
        _currentFolder.value?.let {
            _mediaFolderNameInputState.value = _mediaFolderNameInputState.value.copy(
                text = it.name
            )
//            _mediaFolderDescInputState.value = _mediaFolderDescInputState.value.copy(
//                text = it.description
//            )
//            _mediaFolderAccessLevel.value = when(it.access){
//                AccessLevel.Private.label -> AccessLevel.Private
//                AccessLevel.Public.label -> AccessLevel.Public
//                AccessLevel.Shared.label -> AccessLevel.Shared
//                else -> AccessLevel.Private
//            }
        }
    }

    fun initNavArgAlbumID(albumId: String?){
        //get album
        albumId?.let {
            if(it.isNotEmpty()){
                _currentFolder.value = getFolders(id = it.toLong())
                Log.d(TAG, "initNavArgAlbumID: ${getFolders(id = it.toLong())}")
            }
        }
    }

    fun setBitmap(bitmap: Bitmap?){
        _bitmap.value = bitmap
        addBitmapToMediaImages(bitmap)
    }

    fun setImageUri(uri: Uri?){
        _imageUri.value = uri
        addImageUriToMediaImages(uri)
    }

    private fun addImageUriToMediaImages(uri: Uri?){
        val mediaImages = ArrayList<MediaMeta>(_mediaImages.value)
        uri?.let {
//            mediaImages.add(
//                MediaMeta(
//                    id = System.currentTimeMillis(),
//                    filename = "kitchen.png",
//                    summery = "This is summery",
//                    contentType = "",
//                    height = 34,
//                    width = 34,
//                    size = 34,
//                    imageUri = it,
//                    uploadedDate =  353535,
//                    uploaderId = 234,
//                    accessLevel = "Private"
//                )
//            )
        }
        _mediaImages.value = mediaImages
    }

    fun addBitmapToMediaImages(bitmap: Bitmap?){
        val mediaImages = ArrayList<MediaMeta>(_mediaImages.value)
        bitmap?.let {
//            mediaImages.add(
//                MediaMeta(
//                    id = System.currentTimeMillis(),
//                    filename = "kitchen.png",
//                    summery = "This is summery",
//                    contentType = "",
//                    height = 34,
//                    width = 34,
//                    size =34,
//                    bitmap = it,
//                    uploadedDate =  353535,
//                    uploaderId = 234,
//                    accessLevel = "Private"
//                )
//            )
        }
        _mediaImages.value = mediaImages
    }

    private fun getMedias(){
        _mediaImages.value = listOf(
//            MediaMeta(
//                id = R.drawable.arch4.toLong(),
////                        imageUrl = ,
//                filename = "kitchen.png",
//                summery = "This is summery",
//                contentType = "",
//                height = 34,
//                width = 34,
//                size =34,
//                uploadedDate =  353535,
//                uploaderId = 234,
//                accessLevel = "Private"
//            ),
//            MediaMeta(
//                id = R.drawable.arch5.toLong(),
//                filename = "kitchen.png",
//                summery = "This is summery",
//                contentType = "",
//                height = 34,
//                width = 34,
//                size =34,
//                uploadedDate =  353535,
//                uploaderId = 234,
//                accessLevel = "Private"
//            ),
//            MediaMeta(
//                id = R.drawable.arch6.toLong(),
//                filename = "kitchen.png",
//                summery = "This is summery",
//                contentType = "",
//                height = 34,
//                width = 34,
//                size =34,
//                uploadedDate =  353535,
//                uploaderId = 234,
//                accessLevel = "Private"
//            )
        )

//        _currentFolder.value = MediaAlbum(
//            id = 5,
//            name = "Family",
//            description = "This is kitchen album",
//            updatedDate = System.currentTimeMillis(),
//            ownerId = 23,
//            ownerFamilyId = 45,
//            access = AccessLevel.Public.label,
//            medias = listOf(
//                MediaMeta(
//                    id = R.drawable.arch4.toLong(),
//                    filename = "kitchen.png",
//                    summery = "This is summery",
//                    contentType = "",
//                    height = 34,
//                    width = 34,
//                    size =34,
//                    uploadedDate =  353535,
//                    uploaderId = 234,
//                    accessLevel = "Private"
//                ),
//                MediaMeta(
//                    id = R.drawable.arch5.toLong(),
//                    filename = "kitchen.png",
//                    summery = "This is summery",
//                    contentType = "",
//                    height = 34,
//                    width = 34,
//                    size =34,
//                    uploadedDate =  353535,
//                    uploaderId = 234,
//                    accessLevel = "Private"
//                ),
//                MediaMeta(
//                    id = R.drawable.arch6.toLong(),
//                    filename = "kitchen.png",
//                    summery = "This is summery",
//                    contentType = "",
//                    height = 34,
//                    width = 34,
//                    size =34,
//                    uploadedDate =  353535,
//                    uploaderId = 234,
//                    accessLevel = "Private"
//                )
//            )
//        )
    }

    fun getFolders(id: Long? = null): MediaAlbum {
        /***
         * Simulating the fetching data. It will be replaced later from
         * API call, Only for testing purpose , it will be removed in future
         * **/
//        val foldersData = listOf(
//            MediaAlbum(
//                id = 1,
//                name = "Kitchen",
//                description = "This is kitchen album",
//                updatedDate = System.currentTimeMillis(),
//                ownerId = 23,
//                ownerFamilyId = 45,
//                access = AccessLevel.Public.label,
//                medias = emptyList()
//            ),
//            MediaAlbum(
//                id = 456,
//                name = "Kitchen",
//                description = "This is kitchen album",
//                updatedDate = System.currentTimeMillis(),
//                ownerId = 23,
//                ownerFamilyId = 45,
//                access = AccessLevel.Public.label,
//                medias = emptyList()
//            ),MediaAlbum(
//                id = 45566,
//                name = "Kitchen",
//                description = "This is kitchen album",
//                updatedDate = System.currentTimeMillis(),
//                ownerId = 23,
//                ownerFamilyId = 45,
//                access = AccessLevel.Public.label,
//                medias = emptyList()
//            ),
//            MediaAlbum(
//                id = 2,
//                name = "Garden",
//                description = "This is kitchen album",
//                updatedDate = System.currentTimeMillis(),
//                ownerId = 23,
//                ownerFamilyId = 45,
//                access = AccessLevel.Public.label,
//                medias = emptyList()
//            ),
//            MediaAlbum(
//                id = 3,
//                name = "Bedroom",
//                description = "This is kitchen album",
//                updatedDate = System.currentTimeMillis(),
//                ownerId = 23,
//                ownerFamilyId = 45,
//                access = AccessLevel.Public.label,
//                medias = emptyList()
//            ),
//            MediaAlbum(
//                id = 5,
//                name = "Family",
//                description = "This is kitchen album",
//                updatedDate = System.currentTimeMillis(),
//                ownerId = 23,
//                ownerFamilyId = 45,
//                access = AccessLevel.Public.label,
//                medias = listOf(
//                    MediaMeta(
//                        id = R.drawable.arch4.toLong(),
////                        imageUrl = ,
//                        filename = "kitchen.png",
//                        summery = "This is summery",
//                        contentType = "",
//                        height = 34,
//                        width = 34,
//                        size =34,
//                        uploadedDate =  353535,
//                        uploaderId = 234,
//                        accessLevel = "Private"
//                    ),
//                    MediaMeta(
//                        id = R.drawable.arch5.toLong(),
//                        filename = "kitchen.png",
//                        summery = "This is summery",
//                        contentType = "",
//                        height = 34,
//                        width = 34,
//                        size =34,
//                        uploadedDate =  353535,
//                        uploaderId = 234,
//                        accessLevel = "Private"
//                    ),
//                    MediaMeta(
//                        id = R.drawable.arch6.toLong(),
//                        filename = "kitchen.png",
//                        summery = "This is summery",
//                        contentType = "",
//                        height = 34,
//                        width = 34,
//                        size =34,
//                        uploadedDate =  353535,
//                        uploaderId = 234,
//                        accessLevel = "Private"
//                    )
//                )
//            ),
//            MediaAlbum(
//                id = 6,
//                name = "Home",
//                description = "This is kitchen album",
//                updatedDate = System.currentTimeMillis(),
//                ownerId = 23,
//                ownerFamilyId = 45,
//                access = AccessLevel.Public.label,
//                medias = emptyList()
//            ),
//            MediaAlbum(
//                id = 11,
//                name = "Home",
//                description = "This is kitchen album",
//                updatedDate = System.currentTimeMillis(),
//                ownerId = 23,
//                ownerFamilyId = 45,
//                access = AccessLevel.Public.label,
//                medias = emptyList()
//            ),
//            MediaAlbum(
//                id = 12,
//                name = "Home",
//                description = "This is kitchen album",
//                updatedDate = System.currentTimeMillis(),
//                ownerId = 23,
//                ownerFamilyId = 45,
//                access = AccessLevel.Public.label,
//                medias = emptyList()
//            ),
//            MediaAlbum(
//                id = 10,
//                name = "Family",
//                description = "This is kitchen album",
//                updatedDate = System.currentTimeMillis(),
//                ownerId = 23,
//                ownerFamilyId = 45,
//                access = AccessLevel.Public.label,
//                medias = listOf(
//                    MediaMeta(
//                        id = R.drawable.arch4.toLong(),
////                        imageUrl = ,
//                        filename = "kitchen.png",
//                        summery = "This is summery",
//                        contentType = "",
//                        height = 34,
//                        width = 34,
//                        size =34,
//                        uploadedDate =  353535,
//                        uploaderId = 234,
//                        accessLevel = "Private"
//                    ),
//                    MediaMeta(
//                        id = R.drawable.arch5.toLong(),
//                        filename = "kitchen.png",
//                        summery = "This is summery",
//                        contentType = "",
//                        height = 34,
//                        width = 34,
//                        size =34,
//                        uploadedDate =  353535,
//                        uploaderId = 234,
//                        accessLevel = "Private"
//                    ),
//                    MediaMeta(
//                        id = R.drawable.arch6.toLong(),
//                        filename = "kitchen.png",
//                        summery = "This is summery",
//                        contentType = "",
//                        height = 34,
//                        width = 34,
//                        size =34,
//                        uploadedDate =  353535,
//                        uploaderId = 234,
//                        accessLevel = "Private"
//                    )
//                )
//            ),
//        )
//        _folders.value = foldersData
        lateinit var resultMedia: MediaAlbum
//        if(id != null){
//            val mediaFolder = foldersData.find { it.id == id }
//            val index = foldersData.indexOf(mediaFolder)
//            if(index == -1){
//                resultMedia = MediaAlbum(
//                    id = System.currentTimeMillis(),
//                    name = "Garden",
//                    description = "This is kitchen album",
//                    updatedDate = System.currentTimeMillis(),
//                    ownerId = 23,
//                    ownerFamilyId = 45,
//                    access = AccessLevel.Public.label,
//                    medias = emptyList()
//                )
//            }else{
//                try {
//                    resultMedia  = foldersData[foldersData.indexOf(mediaFolder)]
//                }catch (e: Exception){
//                    Log.e(TAG, "getFolders: ${e.printStackTrace()}")
//                }
//            }
//        }
        return resultMedia
    }
}


