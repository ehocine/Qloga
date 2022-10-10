package eac.qloga.android.features.p4p.showroom.shared.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.bare.dto.media.MediaAlbum

@Composable
fun ListOutAlbums(
    folders: List<MediaAlbum>,
    selectedFolder: MediaAlbum?,
    onSelectFolder: (MediaAlbum) -> Unit,
    onCancel: () -> Unit = {},
    onMove: () -> Unit = {}
) {
    val lazyGridState  = rememberLazyGridState()
    val scrollState = rememberScrollState()
    val containerHorizontalPadding = 24.dp

    Box(modifier = Modifier.fillMaxWidth()){
        Column(modifier = Modifier.fillMaxWidth()){
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                state = lazyGridState,
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(start = 16.dp, end = 15.dp, top = 16.dp , bottom = 8.dp)
            ){
                items(folders, key = {it} ){ folder ->
                    AlbumsFolderPreview(
                        iconId = folder.avatarId?.toInt() ?: R.drawable.ic_ql_empty_folder, //TODO
                        folderName = folder.name,
                        mediaCount = folder.medias.count(),
                        isSelected = folder == selectedFolder,
                        onClick = {
                            Log.d("TAG", "ShowAlbumBottomSheet: clicked")
                            onSelectFolder(folder)
                        }
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
        }

//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter)
//        ) {
//            DividerLine()
//            Row(
//                modifier= Modifier
//                    .fillMaxWidth()
//                    .height(IntrinsicSize.Min)
//                    .background(MaterialTheme.colorScheme.background)
//                ,
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Box(
//                    modifier = Modifier
//                        .weight(2f)
//                        .fillMaxHeight()
//                        .clickable { onCancel() }
//                    ,
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "Cancel",
//                        style = MaterialTheme.typography.titleMedium,
//                        color = orange1
//                    )
//                }
//                Divider(
//                    Modifier
//                        .height(54.dp)
//                        .width(1.dp)
//                        .alpha(.2f)
//                        .background(gray1))
//                Box(
//                    modifier = Modifier
//                        .weight(2f)
//                        .fillMaxHeight()
//                        .clickable { onMove() }
//                    ,
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "Move",
//                        style = MaterialTheme.typography.titleMedium,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                }
//            }
//        }
    }
}