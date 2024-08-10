package indi.stream.repositorychecker.api

import indi.stream.repositorychecker.file
import indi.stream.repositorychecker.pullOrClone
import indi.stream.repositorychecker.repository
import indi.stream.repositorychecker.sshInit
import java.io.File

typealias FolderChecker = (File) -> List<String>?

class RepositoryChecker(private val config: RepositoryConfig) {
    companion object {
        init {
            sshInit()
        }
    }

    private val repository = config.path.file.repository
    private val folderCheckers = mutableListOf<FolderChecker>()

    constructor(config: RepositoryConfig, block: RepositoryChecker.() -> Unit) : this(config) {
        block()
    }

    fun check(checker: FolderChecker) {
        folderCheckers.add(checker)
    }

    operator fun invoke() =
        repository.pullOrClone(config.url).let {
            folderCheckers.asSequence()
                .mapNotNull { it(repository.directory) }
                .flatten()
                .toList()
        }
}

