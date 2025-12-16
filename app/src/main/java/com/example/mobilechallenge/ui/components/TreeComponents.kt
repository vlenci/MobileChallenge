package com.example.mobilechallenge.ui.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilechallenge.R
import com.example.mobilechallenge.repositories.TreeNode
import com.example.mobilechallenge.ui.viewmodels.TreeViewModel
import kotlin.collections.forEach

@Composable
fun Tree(
    nodes: List<TreeNode>?,
    indent: String = "",
    treeViewModel: TreeViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp)
    ) {
        if (nodes != null) {
            nodes.forEach { node ->
                TreeNodeItem(node, "", treeViewModel)
            }
        } else {
            Text("Árvore vazia")
        }
    }
}

@Composable
fun TreeNodeItem(
    node: TreeNode,
    indent: String = "",
    treeViewModel: TreeViewModel,
) {
    var isAssetExpanded by remember { mutableStateOf(false) }

    val assetModifier = Modifier.combinedClickable(
            onClick = if (node.children.isNotEmpty()) {
                { isAssetExpanded = !isAssetExpanded }
            } else {
                {}
            },
            onLongClick = {
                treeViewModel.showEditBottomSheet.value = true
                treeViewModel.assetName.value = node.name
            }
        )


    Row(
        modifier = assetModifier.padding(vertical = 20.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = when (isAssetExpanded) {
                true -> {
                    painterResource(R.drawable.folder_open_24px)
                }
                false -> {
                    if (node.level == 3)
                        painterResource(R.drawable.home_repair_service_24px)
                    else if (node.children.isEmpty())
                        painterResource(R.drawable.folder_24px_filled)
                    else {
                        painterResource(R.drawable.folder_24px)
                    }

                }
            },
            tint = Color.Black,
            contentDescription = null,
        )

        Spacer(Modifier.padding(horizontal = 4.dp))

        Text(
            text = if (node.tag.isNullOrEmpty()) "$indent ${node.name}"
            else "$indent ${node.name} - ${node.tag}",
            fontSize = 16.sp
        )
    }

    if (node.children.isNotEmpty()) {
        if (isAssetExpanded)
            Tree(node.children, "$indent  ", treeViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBottomSheet (
    treeViewModel: TreeViewModel
) {

    ModalBottomSheet(
        onDismissRequest = { treeViewModel.showEditBottomSheet.value = false },
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Edit asset name",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(20.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            Color(0xFF575757),
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                    },
                value = treeViewModel.assetName.value ,
                onValueChange = { treeViewModel.assetName.value = it },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        tint = Color(0xFFFF325F),
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                )
            )

            Spacer(modifier = Modifier.size(32.dp))

            Button(
                onClick = {},
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    .size(
                        width = 320.dp,
                        height = 56.dp
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF325F),
                    disabledContainerColor = Color(0xFFFF325F),
                    disabledContentColor = Color(0xFFFF325F)
                )
            ) {
                Text(
                    text = "Confirm",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
                    )
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Button(
                onClick = { treeViewModel.showEditBottomSheet.value = false },
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .size(
                        width = 320.dp,
                        height = 56.dp
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0),
                    disabledContainerColor = Color(0xFFF0F0F0),
                    disabledContentColor = Color(0xFFF0F0F0)
                )
            ) {
                Text(
                    text = "Cancel",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                        color = Color(0xFFFF325F)
                    )
                )
            }
        }
    }
}

