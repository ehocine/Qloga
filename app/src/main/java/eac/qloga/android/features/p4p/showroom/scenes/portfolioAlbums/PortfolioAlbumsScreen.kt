package eac.qloga.android.features.p4p.showroom.scenes.portfolioAlbums

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.utils.BottomSheetType
import eac.qloga.android.core.shared.utils.DateConverter
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.*
import eac.qloga.bare.enums.AccessLevel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun PortfolioAlbumsScreen(
    navController: NavController,
    parentRouteParam: String? = null,
    viewModel: PortfolioAlbumsViewModel = hiltViewModel()
    ) {
    val titleBarHeight = 68.dp
    val context = LocalContext.current
    val folders = viewModel.folders.value
    val selectedFolders = viewModel.selectedFolders.value
    val isUpdateType  = remember{ mutableStateOf(false) }
    val showDeleteDialog = remember{ mutableStateOf(false)}
    val bottomSheetType = remember{ mutableStateOf<BottomSheetType>(BottomSheetType.Create) }
    val parentRoute = remember { mutableStateOf<String?>(null)}
    
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    LaunchedEffect(Unit){
        parentRoute.value = parentRouteParam
        Log.d(TAG, "AlbumsScreen: ${parentRoute.value}")
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            when(bottomSheetType.value){
                is BottomSheetType.Info -> {
                    MediaInfoCard(
                        access = if(selectedFolders.isNotEmpty()) selectedFolders[0].access else AccessLevel.PRIVATE,
                        name = if(selectedFolders.isNotEmpty()) selectedFolders[0].name else "",
                        owner = "John Smith",
                        label = "album"
                    )
                }
                is BottomSheetType.Create -> {
                    CreateNewAlbum(
                        albumNameState = viewModel.albumNameInputState.value,
                        albumDescState = viewModel.albumDescInputState.value,
                        accessLevel = viewModel.accessLevel.value,
                        title = if(isUpdateType.value) "Update album " else "Create new album",
                        submitButtonName = if(isUpdateType.value) "Update" else "Create",
                        onEnterName = { viewModel.onTriggerEvent(AlbumEvent.EnterAlbumName(it)) },
                        onFocusName = { viewModel.onTriggerEvent(AlbumEvent.FocusAlbumName(it))},
                        onFocusDesc = { viewModel.onTriggerEvent(AlbumEvent.FocusAlbumDesc(it))},
                        onEnterDesc = { viewModel.onTriggerEvent(AlbumEvent.EnterAlbumDesc(it))},
                        onChangeAccess = { viewModel.onTriggerEvent(AlbumEvent.ChangeAccessLevel(it))},
                        onCreate = {
                            scope.launch {
                                if(viewModel.albumNameInputState.value.text.isEmpty()){
                                    Toast.makeText(
                                        context,
                                        "Album name can't be empty!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }else{
                                    modalBottomSheetState.hide()
                                    if(isUpdateType.value){
                                        if(selectedFolders.isNotEmpty()){
                                            viewModel.clearSelectedFolders()
                                            viewModel.onTriggerEvent(AlbumEvent.UpdateAlbum(selectedFolders[0].id))
                                        }
                                    }else{
                                        viewModel.onTriggerEvent(AlbumEvent.CreateAlbum)
                                    }
                                }
                            }
                        },
                        onCancel = {
                            scope.launch {
                                modalBottomSheetState.hide()
                            }
                        }
                    )
                }
                else -> {}
            }
        }
    ) {
        Scaffold(
            topBar = {
                AnimatedVisibility(
                    visible = viewModel.isFolderSelectable.value,
                    enter = scaleIn(
                        animationSpec = spring(
                        stiffness = Spring.StiffnessMedium
                        )
                    ),
                    exit = scaleOut(
                        animationSpec = spring(
                        stiffness = Spring.StiffnessMedium
                        )
                    )
                ) {
                    FolderSelectedTitleBar(
                        leftActions = {
                            SelectedTitleBarItem(
                                iconSize = 24.dp,
                                iconId = R.drawable.ic_ql_cross,
                                iconColor = MaterialTheme.colorScheme.onBackground,
                                onClick = {
                                    // remove being selectable to the folders or albums
                                    viewModel.onTriggerEvent(AlbumEvent.CloseSelectable)
                                }
                            )
                            // count is number of folders or albums being selected
                            CountText(count = viewModel.selectedFolders.value.count())
                        },
                        rightActions = {
                            //select all icon
                            SelectedTitleBarItem(
                                iconSize = 24.dp,
                                iconId = R.drawable.ic_ql_select_all,
                                onClick = { viewModel.onTriggerEvent(AlbumEvent.SelectFolderAll) }
                            )
                        }
                    )
                }
                if(!viewModel.isFolderSelectable.value){
                    TitleBar(
                        label = P4pShowroomScreens.PortfolioAlbums.titleName,
                        iconColor = MaterialTheme.colorScheme.primary,
                    ) {
                        navController.navigateUp()
                    }
                }
            }
        ) { paddingValues ->
            val topPadding = paddingValues.calculateTopPadding()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(titleBarHeight))
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(16.dp)
                    ){
                        items(folders, /*TODO make key for id */){ folder ->
                            AlbumsFolderPreview(
                                iconId = folder.avatarId?.toInt() ?: R.drawable.ic_ql_empty_folder,
                                folderName = folder.name,
                                mediaCount = folder.medias.count(),
                                enableClick = true,
                                isSelectable = viewModel.isFolderSelectable.value,
                                isSelected = folder in viewModel.selectedFolders.value,
                                onLongPress = { viewModel.onTriggerEvent(AlbumEvent.LongPressFolder)},
                                onSelect = { viewModel.onTriggerEvent(AlbumEvent.SelectFolder(listOf(folder)))},
                                onClick = {
                                    navController.navigate(P4pScreens.MediaView.route)
                                    /*
                                    if(parentRoute.value == Screen.Account.route){
                                        scope.launch {
                                            //navigate to see full albums images inside this album or folder
                                            navController.navigate(Screen.MediaView.route + "?$ALBUM_ID=${folder.id}"){
                                                launchSingleTop = true
                                            }
                                        }
                                    }else{
                                        scope.launch {
                                            //navigate to see full albums images inside this album or folder
                                            navController.navigate(Screen.Portfolio.route + "?$ALBUM_ID=${folder.id}"){
                                                launchSingleTop = true
                                            }
                                        }
                                    }
                                     */
                                }
                            )
                        }
                    }
                    if(!viewModel.isFolderSelectable.value){
                        // add new album or folder floating button
                        FloatingActionButton(
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.BottomEnd)
                                .padding(24.dp)
                            ,
                            shape = CircleShape,
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.background,
                            onClick = {
                                scope.launch {
                                    bottomSheetType.value = BottomSheetType.Create
                                    isUpdateType.value = false
                                    viewModel.clearCreateAlbumState() //clearing the input field state if already set
                                    //making full expanded screen to the bottom sheet
                                    modalBottomSheetState.animateTo(targetValue = ModalBottomSheetValue.Expanded)
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                        }
                    }else{
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            DividerLine()
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                SelectedTitleBarItem(
                                    iconSize = 24.dp,
                                    iconId = R.drawable.ic_ql_info,
                                    iconColor = gray1,
                                    label = "Info",
                                    onClick = {
                                        scope.launch {
                                            if(viewModel.selectedFolders.value.isNotEmpty()){
                                                bottomSheetType.value = BottomSheetType.Info
                                                modalBottomSheetState.show()
                                            }
                                        }
                                    }
                                )
                                Spacer(Modifier.width(24.dp))
                                SelectedTitleBarItem(
                                    iconSize = 24.dp,
                                    iconId = R.drawable.ic_ql_edit,
                                    iconColor = gray1,
                                    label = "Edit",
                                    onClick = {
                                        scope.launch {
                                            if(viewModel.selectedFolders.value.isNotEmpty()){
                                                isUpdateType.value = true
                                                bottomSheetType.value = BottomSheetType.Create
                                                // resetting the input field states with the folder state to be updated
                                                viewModel.replaceStateToUpdate(selectedFolders[0].id)
                                                modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)

                                            }
                                        }
                                    }
                                )
                                Spacer(Modifier.width(24.dp))
                                SelectedTitleBarItem(
                                    iconSize = 24.dp,
                                    iconId = R.drawable.ic_ql_delete,
                                    label = "Delete",
                                    iconColor = gray1,
                                    onClick = {
                                        if(viewModel.selectedFolders.value.isNotEmpty()){
                                            showDeleteDialog.value = true
                                        }
                                    }
                                )
                            }
                        }
                    }

                    if(showDeleteDialog.value){
                       DeleteConfirmDialog(
                           // if multiple folders are selected to delete then
                           // show the only first folder name and other in +count
                           name = if (selectedFolders.isNotEmpty()) {
                               selectedFolders[0].name
                           } else {
                               "" + if (selectedFolders.size > 1) " +${selectedFolders.size - 1} more" else ""
                           },
                           updatedDate =  DateConverter.longToStringDate(
                               (if(selectedFolders.isNotEmpty()) {
                                   selectedFolders[0].updatedDate
                               } else { 0 }) as Long
                           ),
                           onDelete = { viewModel.onTriggerEvent(AlbumEvent.DeleteAlbum)}
                       ) {
                           showDeleteDialog.value = false
                       }
                    }
                }
            }
        }
    }
}

