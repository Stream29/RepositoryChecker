package indi.stream.repositorychecker

import indi.stream.repositorychecker.api.RepositoryChecker
import indi.stream.repositorychecker.api.RepositoryConfig
import indi.stream.repositorychecker.api.checkSubfolder
import indi.stream.repositorychecker.api.findAnyContains


fun main() {
    RepositoryChecker(
        RepositoryConfig(
            Config.url,
            Config.path
        )
    ).checkSubfolder { studentFolder ->
        if (studentFolder.isFile) emptySequence()
        else findAnyContains("vue")(studentFolder).map { it + studentFolder.name } + findAnyContains("manager")(
            studentFolder
        ).map { it + studentFolder.name }
    }.forEach { println(it) }
}