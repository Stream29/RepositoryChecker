package indi.stream.repositorychecker.api

import indi.stream.repositorychecker.file
import indi.stream.repositorychecker.pullOrClone
import indi.stream.repositorychecker.repository
import indi.stream.repositorychecker.sshInit
import java.io.File

typealias FolderChecker = (File) -> Sequence<String>

class RepositoryChecker(private val config: RepositoryConfig) {
    companion object {
        init {
            sshInit()
        }
    }

    private val repository = config.path.file.repository

    fun check(checker: FolderChecker) =
        repository.pullOrClone(config.url).let { checker(repository.directory) }
}

