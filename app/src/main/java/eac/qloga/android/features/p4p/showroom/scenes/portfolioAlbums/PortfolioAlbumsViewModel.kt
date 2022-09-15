package eac.qloga.android.features.p4p.showroom.scenes.portfolioAlbums

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.R
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.bare.dto.media.MediaAlbum
import eac.qloga.bare.dto.media.MediaMeta
import eac.qloga.bare.enums.AccessLevel
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

const val TAG = "AlbumViewModel"

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PortfolioAlbumsViewModel @Inject constructor(): ViewModel(){

    private val _folders = mutableStateOf<List<MediaAlbum>>(emptyList())
    val folders: State<List<MediaAlbum>> = _folders

    private val _albumNameInputState = mutableStateOf(InputFieldState(hint = "Enter album name"))
    val albumNameInputState: State<InputFieldState> = _albumNameInputState

    private val _albumDescInputState = mutableStateOf(InputFieldState(hint = "Description(optional)"))
    val albumDescInputState: State<InputFieldState> = _albumDescInputState

    private val _accessLevel = mutableStateOf<AccessLevel>(AccessLevel.PRIVATE)
    val accessLevel: State<AccessLevel> = _accessLevel

    private val _isFolderSelectable = mutableStateOf(false)
    val isFolderSelectable: State<Boolean> = _isFolderSelectable

    private val _selectedFolders = mutableStateOf<List<MediaAlbum>>(emptyList())
    val selectedFolders: State<List<MediaAlbum>> = _selectedFolders

    init {
        getFolders()
    }

    fun onTriggerEvent(event: AlbumEvent){
         try {
            viewModelScope.launch {
              when(event){
                  is AlbumEvent.SelectFolder -> { onSelectFolder(event.folders) }
                  is AlbumEvent.SelectFolderAll -> { onSelectFolderAll() }
                  is AlbumEvent.EnterAlbumName -> { onAlbumName(event.name) }
                  is AlbumEvent.EnterAlbumDesc -> { onAlbumDesc(event.desc) }
                  is AlbumEvent.FocusAlbumName -> { onFocusAlbumName(event.focusState) }
                  is AlbumEvent.FocusAlbumDesc -> { onFocusAlbumDesc(event.focusState) }
                  is AlbumEvent.ChangeAccessLevel -> { onChangeAccessLevel(event.access) }
                  is AlbumEvent.CreateAlbum -> { onCreateAlbum() }
                  is AlbumEvent.DeleteAlbum -> { onDeleteAlbum() }
                  is AlbumEvent.CloseSelectable -> { onCloseSelectable() }
                  is AlbumEvent.UpdateAlbum -> { onUpdateAlbum(event.albumId) }
                  is AlbumEvent.LongPressFolder -> { _isFolderSelectable.value = !isFolderSelectable.value }
              }
            }
         }catch (e: Exception){
             Log.e(TAG, "onTriggerEvent: ${e.printStackTrace()}")
         }
    }

    private fun onCloseSelectable() {
        _isFolderSelectable.value = false
        clearSelectedFolders()
    }

    fun clearSelectedFolders(){
        _selectedFolders.value = emptyList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onCreateAlbum() {
        //later save into server
        val albums = ArrayList<MediaAlbum>(_folders.value)

        albums.add(
            MediaAlbum().apply {
                id = Math.random().toLong()
                name = albumNameInputState.value.text
                descr = albumDescInputState.value.text
                updatedDate = ZonedDateTime.now()
                ownerId = Math.random().toLong()
                ownerFamilyId = Math.random().toLong()
                medias = emptyList()
                access = accessLevel.value
            }
        )
        _folders.value = albums
        clearCreateAlbumState()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onUpdateAlbum(albumId: Long) {
        //later edit and update into server
        val albums = ArrayList<MediaAlbum>(_folders.value)
        val album = folders.value.find { it.id == albumId }
        albums.remove(album)
        albums.add(
            MediaAlbum().apply {
                id = Math.random().toLong()
                name = albumNameInputState.value.text
                descr = albumDescInputState.value.text
                updatedDate = ZonedDateTime.now()
                ownerId = Math.random().toLong()
                ownerFamilyId = Math.random().toLong()
                medias = emptyList()
                access = accessLevel.value
            }
        )
        _folders.value = albums
        clearCreateAlbumState()
    }

    fun clearCreateAlbumState(){
        _albumNameInputState.value = albumNameInputState.value.copy(
            text = ""
        )
        _albumDescInputState.value = albumDescInputState.value.copy(
            text = ""
        )
        _accessLevel.value  = AccessLevel.PRIVATE
    }

    private fun onDeleteAlbum() {
        // later delete into server
        // removing all albums which are selected
        val albums = ArrayList<MediaAlbum>(_folders.value)
        albums.removeAll(selectedFolders.value.toSet())
        _folders.value = albums
        _selectedFolders.value =  emptyList()
    }

    private fun onFocusAlbumName(focusState: FocusState) {
        _albumNameInputState.value = albumNameInputState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onFocusAlbumDesc(focusState: FocusState) {
        _albumDescInputState.value = albumDescInputState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onChangeAccessLevel(access: AccessLevel) {
        _accessLevel.value = access
    }

    private fun onAlbumDesc(desc: String) {
        _albumDescInputState.value = albumDescInputState.value.copy(
            text = desc
        )
    }

    private fun onAlbumName(name: String) {
        _albumNameInputState.value = albumNameInputState.value.copy(
            text = name
        )
    }

    private fun onSelectFolder(folders: List<MediaAlbum>){
        val newFolders = ArrayList(this._selectedFolders.value)
        folders.forEach { folder ->
            if(folder in newFolders){
                //remove if folder is already selected
                newFolders.remove(folder)
            }else{
                //add if folder is not selected already
                newFolders.add(folder)
            }
        }
        _selectedFolders.value = newFolders
    }

    private fun onSelectFolderAll(){
        val newFolders = ArrayList(this._selectedFolders.value)
        // selectedFolders size same with portfolio size
        // means the already selected all so remove all
        // selected else add all to selected
        if(_selectedFolders.value.size >= folders.value.size){
            newFolders.clear()
        }else{
            newFolders.addAll(_folders.value)
        }
        _selectedFolders.value = newFolders
    }

    fun replaceStateToUpdate(albumId: Long){
        val folder = folders.value.find { it.id == albumId }
        folder?.let {
            _albumNameInputState.value = albumNameInputState.value.copy(
                text = folder.name
            )
            _albumDescInputState.value = albumDescInputState.value.copy(
                text = folder.descr
            )
            _accessLevel.value = folder.access
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFolders() {
        /***
         * Simulating the fetching data. It will be replaced later from
         * API call, Only for testing , it will be removed in future
         * **/
        val foldersData = listOf(
            MediaAlbum().apply {
                id = 1
                name = "Kitchen"
                descr = "This is kitchen album"
                updatedDate = ZonedDateTime.now()
                ownerId = 23
                ownerFamilyId = 45
                access = AccessLevel.PUBLIC
                medias = emptyList()
            },
            MediaAlbum().apply {
                id = 456
                name = "Kitchen"
                descr = "This is kitchen album"
                updatedDate = ZonedDateTime.now()
                ownerId = 23
                ownerFamilyId = 45
                access = AccessLevel.PUBLIC
                medias = emptyList()
            },
            MediaAlbum().apply {
                id = 3
                name = "Bedroom"
                descr = "This is kitchen album"
                updatedDate = ZonedDateTime.now()
                ownerId = 23
                ownerFamilyId = 45
                access = AccessLevel.PUBLIC
                medias = emptyList()
            },
            MediaAlbum().apply {
                id = 5
                name = "Family"
                descr = "This is kitchen album"
                updatedDate = ZonedDateTime.now()
                ownerId = 23
                ownerFamilyId = 45
                access = AccessLevel.PUBLIC
                medias = listOf(
                    MediaMeta().apply {
                        id = R.drawable.arch4.toLong()
                        filename = "kitchen.png"
                        summary = "This is summery"
                        contentType = ""
                        height = 34
                        width = 34
                        size =34
                        uploadedDate = ZonedDateTime.now()
                        uploaderId = 234
                        access = AccessLevel.PRIVATE
                    },
                    MediaMeta().apply {
                        id = R.drawable.arch5.toLong()
                        filename = "kitchen.png"
                        summary = "This is summery"
                        contentType = ""
                        height = 34
                        width = 34
                        size =34
                        uploadedDate =  ZonedDateTime.now()
                        uploaderId = 234
                        access = AccessLevel.PRIVATE
                    },
                    MediaMeta().apply {
                        id = R.drawable.arch6.toLong()
                        filename = "kitchen.png"
                        summary = "This is summery"
                        contentType = ""
                        height = 34
                        width = 34
                        size =34
                        uploadedDate =  ZonedDateTime.now()
                        uploaderId = 234
                        access = AccessLevel.PRIVATE
                    },
                    MediaMeta().apply {
                        id = R.drawable.arch6.toLong()
                        filename = "kitchen.png"
                        summary = "This is summery"
                        contentType = ""
                        height = 34
                        width = 34
                        size =34
                        uploadedDate = ZonedDateTime.now()
                        uploaderId = 234
                        access = AccessLevel.PRIVATE
                    }
                )
            },
            MediaAlbum().apply {
                id = 6
                name = "Home"
                descr = "This is kitchen album"
                updatedDate = ZonedDateTime.now()
                ownerId = 23
                ownerFamilyId = 45
                access = AccessLevel.PUBLIC
                medias = emptyList()
            },
            MediaAlbum().apply {
                id = 11
                name = "Home"
                descr = "This is kitchen album"
                updatedDate = ZonedDateTime.now()
                ownerId = 23
                ownerFamilyId = 45
                access = AccessLevel.PUBLIC
                medias = emptyList()
            },
            MediaAlbum().apply {
                id = 11
                name = "Home"
                descr = "This is kitchen album"
                updatedDate = ZonedDateTime.now()
                ownerId = 23
                ownerFamilyId = 45
                access = AccessLevel.PUBLIC
                medias = emptyList()
            },
            MediaAlbum().apply {
                id = 5
                name = "Family"
                descr = "This is kitchen album"
                updatedDate = ZonedDateTime.now()
                ownerId = 23
                ownerFamilyId = 45
                access = AccessLevel.PUBLIC
                medias = listOf(
                    MediaMeta().apply {
                        id = R.drawable.arch4.toLong()
                        filename = "kitchen.png"
                        summary = "This is summery"
                        contentType = ""
                        height = 34
                        width = 34
                        size =34
                        uploadedDate = ZonedDateTime.now()
                        uploaderId = 234
                        access = AccessLevel.PRIVATE
                    },
                    MediaMeta().apply {
                        id = R.drawable.arch5.toLong()
                        filename = "kitchen.png"
                        summary = "This is summery"
                        contentType = ""
                        height = 34
                        width = 34
                        size =34
                        uploadedDate =  ZonedDateTime.now()
                        uploaderId = 234
                        access = AccessLevel.PRIVATE
                    },
                    MediaMeta().apply {
                        id = R.drawable.arch6.toLong()
                        filename = "kitchen.png"
                        summary = "This is summery"
                        contentType = ""
                        height = 34
                        width = 34
                        size =34
                        uploadedDate =  ZonedDateTime.now()
                        uploaderId = 234
                        access = AccessLevel.PRIVATE
                    },
                    MediaMeta().apply {
                        id = R.drawable.arch6.toLong()
                        filename = "kitchen.png"
                        summary = "This is summery"
                        contentType = ""
                        height = 34
                        width = 34
                        size =34
                        uploadedDate = ZonedDateTime.now()
                        uploaderId = 234
                        access = AccessLevel.PRIVATE
                    }
                )
            },
        )
        _folders.value = foldersData
    }
}


