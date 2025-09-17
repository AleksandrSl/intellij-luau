package com.github.aleksandrsl.intellijluau.modules

import com.intellij.platform.workspace.jps.entities.ModuleEntity
import com.intellij.platform.workspace.storage.EntityStorage
import com.intellij.workspaceModel.core.fileIndex.WorkspaceFileIndexContributor
import com.intellij.workspaceModel.core.fileIndex.WorkspaceFileSetRegistrar

@Suppress("UnstableApiUsage")
class LuauWorkspaceFileIndexContributor : WorkspaceFileIndexContributor<ModuleEntity> {
    private val CONFIG_FILES = setOf(
        WALLY_LOCK,
        WALLY_TOML,
        ROTRIVER_LOCK,
        ROTRIVER_TOML
    )

    override val entityClass: Class<ModuleEntity>
        get() = ModuleEntity::class.java

    override fun registerFileSets(
        entity: ModuleEntity,
        registrar: WorkspaceFileSetRegistrar,
        storage: EntityStorage
    ) {
        entity.contentRoots.forEach { contentRoot ->
            registrar.registerExclusionCondition(
                contentRoot.url,
                { file ->
                    file.name == PACKAGES_DIR_NAME && file.isDirectory && file.parent.children.any {
                        CONFIG_FILES.contains(
                            it.name
                        )
                    }
                },
                entity
            )
        }
    }

    companion object {
        private const val PACKAGES_DIR_NAME = "Packages"
        const val WALLY_LOCK = "wally.lock"
        const val WALLY_TOML = "wally.toml"
        const val ROTRIVER_LOCK = "rotriever.lock"
        const val ROTRIVER_TOML = "rotriever.toml"
    }
}
