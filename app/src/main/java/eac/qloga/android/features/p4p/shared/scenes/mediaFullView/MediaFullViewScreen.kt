package eac.qloga.android.features.p4p.shared.scenes.mediaFullView

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.utils.BottomSheetType
import eac.qloga.android.core.shared.utils.DateConverter
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.*
import eac.qloga.android.features.p4p.showroom.shared.utils.MediaEvent
import eac.qloga.android.features.p4p.shared.viewmodels.MediaViewModel
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun MediaFullViewScreen(
    navController: NavController,
    viewModel: MediaViewModel,
    mediaMetaId: String? = null,
) {
    val currentFolder = viewModel.currentFolder.value
    val fullViewImage = viewModel.fullViewImage.value
    val imageId = fullViewImage?.id?.toInt() ?: R.drawable.arch4
    val containerHorizontalPadding = 24.dp
    val context = LocalContext.current
    val bottomSheetType = remember{ mutableStateOf<BottomSheetType>(BottomSheetType.Info)}
    val showDeleteDialog = remember{ mutableStateOf(false) }
    val bottomOptionsItemWidth = 58.dp

    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    LaunchedEffect(Unit){
        mediaMetaId?.let {
            viewModel.initializeNavArguments(albumId = it)
        }
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            when(bottomSheetType.value){
                is  BottomSheetType.Edit -> {
                    // sets all the input state with data of image to be edited
                    EditAlbumImage(
                        imageNameState = viewModel.imageNameInputState.value,
                        imageDescState = viewModel.imageDescInputState.value,
                        accessLevel = viewModel.imageAccessLevel.value,
                        title = "Update image",
                        submitButtonName = "Update",
                        onEnterName = { viewModel.onTriggerEvent(MediaEvent.EnterImageName(it)) },
                        onFocusName = { viewModel.onTriggerEvent(MediaEvent.FocusImageName(it)) },
                        onFocusDesc = { viewModel.onTriggerEvent(MediaEvent.FocusImageDesc(it)) },
                        onEnterDesc = { viewModel.onTriggerEvent(MediaEvent.EnterImageDesc(it)) },
                        onChangeAccess = { viewModel.onTriggerEvent(MediaEvent.ChangeImageAccessLevel(it))},
                        onCreate = {
                            scope.launch {
                                if(viewModel.imageNameInputState.value.text.isEmpty()){
                                    Toast.makeText(context, "Image name can't be empty!", Toast.LENGTH_LONG).show()
                                }else{
                                    modalBottomSheetState.hide()
                                    viewModel.onTriggerEvent(MediaEvent.UpdateImage(imageId.toLong()))
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
                        access = /*fullViewImage?.accessLevel ?:*/ "",
                        name = fullViewImage?.filename ?: "",
                        owner = "John Smith",
                        label = "image"
                    )
                }
                else -> {
                    Text(text = "Now bottom sheet ")
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
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
                if(!viewModel.isImageSelectable.value){
                    TitleBar(
                        label = currentFolder?.name ?: "",
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
                Spacer(modifier = Modifier.height(topPadding))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ){
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = imageId),
                        contentDescription = null ,
                        contentScale = ContentScale.FillWidth
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = containerHorizontalPadding, vertical = 8.dp)
                    ,
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SelectedTitleBarItem(
                        modifier = Modifier.width(bottomOptionsItemWidth),
                        iconSize = 24.dp,
                        iconId = R.drawable.ic_ql_info,
                        iconColor = gray1,
                        label = "Info",
                        onClick = {
                            scope.launch {
                                bottomSheetType.value = BottomSheetType.Info
                                modalBottomSheetState.show()
                            }
                        }
                    )
                    SelectedTitleBarItem(
                        modifier = Modifier.width(bottomOptionsItemWidth),
                        iconSize = 24.dp,
                        iconId = R.drawable.ic_ql_upload,
                        iconColor = gray1,
                        label = "Move to",
                        onClick = {
                            scope.launch {
                                navController.navigate(P4pScreens.SelectAlbum.route)
                            }
                        }
                    )
                    SelectedTitleBarItem(
                        modifier = Modifier.width(bottomOptionsItemWidth),
                        iconSize = 24.dp,
                        iconId = R.drawable.ic_ql_add,
                        iconColor = gray1,
                        label = "Album avatar",
                        onClick = {
                            if(viewModel.folderToBeAdded.value != null){
                                //TODO add the selected images to album , later backend
                                // viewModel.setSelectedFolder()
                                Toast.makeText(context, "Added, backend work", Toast.LENGTH_SHORT).show()
                            }else {
                                navController.navigate(P4pShowroomScreens.PortfolioAlbums.route)
                            }
                        }
                    )
                    SelectedTitleBarItem(
                        modifier = Modifier.width(bottomOptionsItemWidth),
                        iconSize = 24.dp,
                        iconId = R.drawable.ic_ql_edit,
                        iconColor = gray1,
                        label = "Edit",
                        onClick = {
                            scope.launch {
                                bottomSheetType.value = BottomSheetType.Edit
                                viewModel.setStateEditImage()
                                modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        }
                    )
                    SelectedTitleBarItem(
                        modifier = Modifier.width(bottomOptionsItemWidth),
                        iconSize = 24.dp,
                        iconId = R.drawable.ic_ql_delete,
                        iconColor = gray1,
                        label = "Delete",
                        onClick = {
                            showDeleteDialog.value = true
                        }
                    )
                }

                if(showDeleteDialog.value){
                    DeleteConfirmDialog(
                        name = fullViewImage?.filename ?: "",
                        updatedDate =  DateConverter.longToStringDate(
                            (if(viewModel.selectedImages.value.isNotEmpty()) {
                                viewModel.selectedImages.value[0].uploadedDate
                            } else { 0 }) as Long
                        ),
                        onDelete = {
                            scope.launch {
                                viewModel.onTriggerEvent(MediaEvent.DeleteImage)
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                                //navController.navigate(Screen.MediaView.route)
                            }
                        },
                        onDismissDialog = {
                            showDeleteDialog.value = false
                        }
                    )
                }
            }
        }
    }
}


