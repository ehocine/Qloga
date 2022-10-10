package eac.qloga.android.features.p4p.shared.scenes.mediaView

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.BottomSheetType
import eac.qloga.android.core.shared.utils.DateConverter
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.*
import eac.qloga.android.features.p4p.showroom.shared.utils.MediaEvent
import eac.qloga.android.features.p4p.shared.viewmodels.MediaViewModel
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class
)
@Composable
fun MediaViewScreen(
    navController: NavController,
    viewModel: MediaViewModel,
    albumId: String? = null,
) {
    val titleBarHeight = 68.dp
    val context = LocalContext.current
    val showDeleteDialog = viewModel.showDeleteDialog.value
    val bottomSheetType = remember{ mutableStateOf<BottomSheetType>(BottomSheetType.Create)}

    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    LaunchedEffect(Unit){
        viewModel.initNavArgAlbumID(albumId = albumId)
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            ModalBottomSheetContent(
                bottomSheetType = bottomSheetType.value,
                viewModel = viewModel,
                modalBottomSheetState = modalBottomSheetState,
                navController = navController
            )
        }
    ) {
        Scaffold(
            topBar = {
                TitleBarContent(
                    viewModel = viewModel,
                    modalBottomSheetState = modalBottomSheetState,
                    navController = navController,
                    onShowDeleteDialog = { viewModel.onShowDeleteDialog(it)},
                    onChangeBottomSheetType = { sheetType -> bottomSheetType.value = sheetType }
                )
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
                        items(viewModel.mediaImages.value, key = {it}){ image ->
//                            AlbumImagePreview(
//                                imageId = image.id.toInt(),
//                                bitmapImage = image.bitmap,
//                                imageUri = image.imageUri,
//                                isSelectable = viewModel.isImageSelectable.value,
//                                isSelected = image in viewModel.selectedImages.value,
//                                onLongPress = { viewModel.onTriggerEvent(MediaEvent.LongPressImage) },
//                                onSelect = {  viewModel.onTriggerEvent(MediaEvent.SelectImage(listOf(image)))  },
//                                onClick = {
//                                    scope.launch {
//                                        navController.navigate(Screen.MediaFullView.route+"?id=${image.id}")
//                                    }
//                                }
//                            )
                        }
                    }

                    if(!viewModel.isImageSelectable.value){
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
                                    bottomSheetType.value = BottomSheetType.Add
                                    modalBottomSheetState.show()
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
                        ) {
                            DividerLine()
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                SelectedTitleBarItem(
                                    iconSize = 24.dp,
                                    iconId = R.drawable.ic_ql_upload,
                                    iconColor = gray1,
                                    label = "Add to album",
                                    onClick = {
                                        if(viewModel.folderToBeAdded.value != null){
                                            viewModel.onSetCurrentFolder(viewModel.folderToBeAdded.value!!)
                                            viewModel.onTriggerEvent(MediaEvent.CloseSelectable)
                                            Toast.makeText(context, "Added, backend should handle rest", Toast.LENGTH_SHORT).show()
                                        }else {
                                            navController.navigate(P4pShowroomScreens.PortfolioAlbums.route)
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
                                        if(viewModel.selectedImages.value.isNotEmpty()){
                                            viewModel.onShowDeleteDialog(true)
                                        }
                                    }
                                )
                            }
                        }
                    }

                    if(showDeleteDialog){
                        DeleteConfirmDialog(
                            name = viewModel.currentFolder.value?.name ?: "",
                            updatedDate =  DateConverter.longToStringDate(
                                (if(viewModel.selectedImages.value.isNotEmpty()) {
                                    viewModel.selectedImages.value[0].uploadedDate
                                } else { 0 }) as Long
                            ),
                            onDelete = {
                                scope.launch {
                                    navController.navigate(P4pShowroomScreens.PortfolioAlbums.route)
                                    viewModel.onTriggerEvent(MediaEvent.DeleteImage)
                                    }
                                },
                            onDismissDialog = { viewModel.onShowDeleteDialog(false) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun TitleBarContent(
    viewModel: MediaViewModel,
    modalBottomSheetState: ModalBottomSheetState,
    navController: NavController,
    onShowDeleteDialog: (Boolean) -> Unit,
    onChangeBottomSheetType: (BottomSheetType) -> Unit

){
    //showMenu for vertMore options to select (edit, info, delete)
    val showMenu = remember{ mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = viewModel.isImageSelectable.value,
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
                    onClick = { viewModel.onTriggerEvent(MediaEvent.CloseSelectable) }
                )
                CountText(count = viewModel.selectedImages.value.count())
            },
            rightActions = {
                //select all icon
                SelectedTitleBarItem(
                    iconSize = 24.dp,
                    iconId = R.drawable.ic_ql_select_all,
                    onClick = { viewModel.onTriggerEvent(MediaEvent.SelectAllImages) }
                )
            }
        )
    }
    // only display when image is not in selectable state
    if(!viewModel.isImageSelectable.value){
        TitleBar(label = viewModel.currentFolder.value?.name ?: "",
            iconColor = MaterialTheme.colorScheme.primary,
            actions = {
                Column{
                    IconButton( onClick = { showMenu.value = !showMenu.value }) {
                        Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null )
                    }
                    if(showMenu.value){
                        Popup(
                            alignment = Alignment.BottomEnd,
                            properties = PopupProperties(),
                            offset = IntOffset(y = 0,x = 0),
                            onDismissRequest = { showMenu.value = false }
                        ) {
                            Box(
                                modifier = Modifier
                                    .shadow(
                                        2.dp,
                                        shape = RoundedCornerShape(16.dp),
                                        spotColor = Color.Black
                                    )
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            scope.launch {
                                                onChangeBottomSheetType(BottomSheetType.Info)
                                                showMenu.value = false
                                                modalBottomSheetState.show()
                                            }
                                        }
                                        .padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            top = 16.dp,
                                            bottom = 12.dp
                                        )){
                                        Text(text = "Album info", style = MaterialTheme.typography.titleSmall)
                                    }
                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            scope.launch {
                                                onChangeBottomSheetType(BottomSheetType.Edit)
                                                showMenu.value = false
                                                modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                                viewModel.setStateEditMediaFolder()
                                            }
                                        }
                                        .padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            top = 12.dp,
                                            bottom = 12.dp
                                        )){
                                        Text(text = "Edit album", style = MaterialTheme.typography.titleSmall)
                                    }
                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            scope.launch {
                                                onShowDeleteDialog(true)
                                                showMenu.value = false
                                            }
                                        }
                                        .padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            top = 12.dp,
                                            bottom = 16.dp
                                        )){
                                        Text(text = "Delete album", style = MaterialTheme.typography.titleSmall)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ) {
            navController.navigateUp()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ModalBottomSheetContent(
    bottomSheetType: BottomSheetType,
    viewModel: MediaViewModel,
    modalBottomSheetState: ModalBottomSheetState,
    navController: NavController
){
    val imageUri = viewModel.imageUri.value
    val isCameraSelected = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setImageUri(uri)
        imageUri?.let {
            if (!isCameraSelected.value) {
                val bitMap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    ImageDecoder.decodeBitmap(source)
                }
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { btm: Bitmap? ->
        viewModel.setBitmap(btm)
        viewModel.setImageUri(null)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (isCameraSelected.value) {
                cameraLauncher.launch()
            } else {
                galleryLauncher.launch("image/*")
            }
            scope.launch {
                modalBottomSheetState.hide()
            }
        } else {
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    when(bottomSheetType){
        is BottomSheetType.Edit -> {
            CreateNewAlbum(
                albumNameState = viewModel.mediaFolderNameInputState.value,
                albumDescState = viewModel.mediaFolderDescInputState.value,
                accessLevel = viewModel.mediaFolderAccessLevel.value,
                title = "Edit the album",
                submitButtonName = "Update",
                onEnterName = { viewModel.onTriggerEvent(MediaEvent.EnterMediaFolderName(it)) },
                onFocusName = { viewModel.onTriggerEvent(MediaEvent.FocusMediaFolderName(it))},
                onFocusDesc = { viewModel.onTriggerEvent(MediaEvent.FocusMediaFolderDesc(it))},
                onEnterDesc = { viewModel.onTriggerEvent(MediaEvent.EnterMediaFolderDesc(it))},
                onChangeAccess = { viewModel.onTriggerEvent(MediaEvent.ChangeMediaFolderAccessLevel(it))},
                onCreate = {
                    scope.launch {
                        if(viewModel.mediaFolderNameInputState.value.text.isEmpty()){
                            Toast.makeText(context, "Album name can't be empty!", Toast.LENGTH_LONG).show()
                        }else{
                            modalBottomSheetState.hide()
                            viewModel.onTriggerEvent(MediaEvent.UpdateMediaFolder)
                            Toast.makeText(context, "Media folder updated !", Toast.LENGTH_SHORT).show()
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
        is BottomSheetType.Info -> {
            InfoCard(
//                access = viewModel.currentFolder.value?.access ?: "",
                access = "",
                name = viewModel.currentFolder.value?.name ?: "",
                owner = "John Smith",
                label = "album"
            )
        }
        is BottomSheetType.Add -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 12.dp)
                    ,
                    contentAlignment = Alignment.Center
                ){
                    Box(
                        modifier = Modifier
                            .height(1.5.dp)
                            .width(70.dp)
                            .clip(CircleShape)
                            .background(gray1)
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.CAMERA
                                ) -> {
                                    cameraLauncher.launch()
                                    scope.launch {
                                        modalBottomSheetState.hide()
                                    }
                                }
                                else -> {
                                    isCameraSelected.value = true
                                    permissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }
                        }
                        .padding(vertical = 16.dp)
                    ,
                    text = "Take a picture",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                DividerLine()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                navController.navigate(P4pShowroomScreens.PortfolioAlbums.route)
                                modalBottomSheetState.hide()
                                viewModel.currentFolder.value?.let {
                                    viewModel.setFolderToBeAdded(it)
                                }
                            }
                        }
                        .padding(vertical = 16.dp)
                    ,
                    text = "From QLOGA media library",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                DividerLine()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.READ_EXTERNAL_STORAGE
                                ) -> {
                                    galleryLauncher.launch("image/*")
                                    scope.launch {
                                        modalBottomSheetState.hide()
                                    }
                                }
                                else -> {
                                    isCameraSelected.value = false
                                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }
                        }
                        .padding(vertical = 16.dp)
                    ,
                    text = "From phone gallery",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                DividerLine()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                modalBottomSheetState.hide()
                            }
                        }
                        .padding(vertical = 16.dp)
                    ,
                    text = "Cancel",
                    style = MaterialTheme.typography.titleMedium,
                    color = orange1,
                    textAlign = TextAlign.Center
                )
            }
        }
        else -> {
            Text(text =" No bottom sheet to display")
        }
    }
}

