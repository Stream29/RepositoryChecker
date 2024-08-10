package indi.stream.repositorychecker.api


fun RepositoryChecker.checkNEUQACMStudents() =
    checkSubfolder { studentFolder ->
        studentFolder.name.curriculum.run {
            requirements.map {
                it(studentFolder)
            }.flatten()
        }
    }

val String.curriculum: String
    get() = TODO()

val String.requirements: Sequence<FolderChecker>
    get() = TODO()


