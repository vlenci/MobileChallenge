package com.example.mobilechallenge.repositories

import android.content.Context
import com.example.mobilechallenge.services.AssetResponse
import com.example.mobilechallenge.services.TreeService
import com.example.mobilechallenge.states.TreeUiState
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import retrofit2.HttpException

interface TreeRepository {

    suspend fun getTree(token: String, siteId: Int): TreeUiState<List<TreeNode>>
    fun buildTreeStructure(
        assets: List<AssetResponse>,
    ): List<TreeNode>
}

data class TreeNode(
    val id: Int,
    val name: String,
    val tag: String?,
    val type: String?,
    val level: Int?,
    val order: Int,
    val children: List<TreeNode> = emptyList(),
)

data class TreeNodeMutable(
    val id: Int,
    val name: String,
    val tag: String?,
    val type: String?,
    val level: Int?,
    val order: Int,
    val children: MutableList<TreeNodeMutable>,
)

class TreeRepositoryImpl @Inject constructor(
    private val treeService: TreeService,
    @ApplicationContext private val context: Context
): TreeRepository {
    override suspend fun getTree(token: String, siteId: Int): TreeUiState<List<TreeNode>> {
        return try {
            val response = treeService.getTree(token, siteId)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    TreeUiState.Success(buildTreeStructure(body.tree))
                } else {
                    TreeUiState.Error("Body vazio")
                }
            } else {
                TreeUiState.Error("Erro na API", response.code())
            }
        } catch (e: HttpException) {
            TreeUiState.Error(e.message ?: "Invalid Credentials", e.code())
        }
    }

    override fun buildTreeStructure(assets: List<AssetResponse>): List<TreeNode> {
        println("🏗️ BUILD TREE DEBUG: Building tree with ${assets.size} assets")
        assets.forEach { asset ->
            println("  - Asset ${asset.id}: name='${asset.name}', parent=${asset.parent}, level=${asset.level}, order=${asset.order}")
        }

        val nodeMap = mutableMapOf<String, TreeNodeMutable>()

        assets.forEach { asset ->
            nodeMap[asset.id.toString()] =
                TreeNodeMutable(
                    id = asset.id,
                    name = asset.name,
                    tag = asset.tag,
                    type = asset.group,
                    level = asset.level,
                    order = asset.order,
                    children = mutableListOf(),
                )
        }

        val rootNodes = mutableListOf<TreeNodeMutable>()
        assets.forEach { asset ->
            val node = nodeMap[asset.id.toString()]!!
            val parentId = asset.parent // Store parentId in a variable

            // Check if parentId is null or 0 first
            if (parentId == null || parentId == 0) {
                println("  - Adding ${asset.name} (id=${asset.id}, parentId=null/0) as ROOT node")
                rootNodes.add(node)
            } else {
                // parentId is not null or 0, convert to string for map lookup
                val parentNode = nodeMap[parentId.toString()]
                if (parentNode != null) {
                    println("  - Adding ${asset.name} (id=${asset.id}) as CHILD of ${parentNode.name} (id=${parentNode.id})")
                    parentNode.children.add(node)
                } else {
                    // Parent ID exists but not found in nodeMap, treat as root and log warning
                    println("  - WARNING: Parent with id=$parentId for asset ${asset.name} (id=${asset.id}) not found in nodeMap. Adding as ROOT node.")
                    rootNodes.add(node)
                }
            }
        }

        fun sortChildren(node: TreeNodeMutable) {
            node.children.sortBy { it.order }
            node.children.forEach { sortChildren(it) }
        }
        rootNodes.forEach { sortChildren(it) }

        fun toImmutable(node: TreeNodeMutable): TreeNode =
            TreeNode(
                id = node.id,
                name = node.name,
                tag = node.tag,
                type = node.type,
                level = node.level,
                order = node.order,
                children = node.children.map { toImmutable(it) },
            )

        val result = rootNodes.sortedBy { it.order }.map { toImmutable(it) }

        println("🏗️ BUILD TREE DEBUG: Final tree structure:")

        fun printTree(
            nodes: List<TreeNode>,
            indent: String = "",
        ) {
            nodes.forEach { node ->
                println("$indent- ${node.name} (id=${node.id}, level=${node.level}, children=${node.children.size})")
                if (node.children.isNotEmpty()) {
                    printTree(node.children, "$indent  ")
                }
            }
        }
        printTree(result)

        return result
    }

}