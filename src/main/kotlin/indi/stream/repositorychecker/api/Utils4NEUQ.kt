package indi.stream.repositorychecker.api

fun RepositoryChecker.checkNEUQACMStudents() =
    checkSubfolder { studentFolder ->
        studentFolder.name.curriculum.run {
            requirements.map {
                it(studentFolder)
            }.flatten()
        }
    }

fun FolderChecker.specialize(
    studentName: String,
    weekName: String,
): FolderChecker =
    { file ->
        val weekFolder = file.resolve(weekName)
        (if (weekFolder.exists()) this(weekFolder) else sequenceOf("文件夹未创建"))
            .map { "${"$studentName/$weekName: "}: $it" }
    }

val String.curriculum: String
    get() = TODO()

val String.requirements: Sequence<FolderChecker>
    get() = TODO()


