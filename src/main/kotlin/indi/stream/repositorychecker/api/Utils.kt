package indi.stream.repositorychecker.api

import java.io.File

fun RepositoryChecker.checkSubfolder(checker: FolderChecker) =
    check {
        it.listFiles()
            .orEmpty()
            .asSequence()
            .map(checker)
            .flatten()
    }

fun findAnyContains(text: String): FolderChecker =
    { file ->
        file.subFilesWithoutFolder
            .filter { it.name.contains(text) }
            .count().let{
                if (it == 0) sequenceOf("未找到名称包含\"${text}\"的文件")
                else emptySequence()
            }
    }

// The receiver should be a directory, otherwise NullPointerException will be thrown
// Without this file itself
val File.subFilesWithoutFolder: Sequence<File>
    get() = this.listFiles()!!.asSequence().flatMap { if (it.isDirectory) it.subFilesWithoutFolder else sequenceOf(it) }

// The receiver should be a directory, otherwise NullPointerException will be thrown
// Including this file itself
val File.subFilesWithFolder: Sequence<File>
    get() = sequenceOf(this) +
            this.listFiles().orEmpty().asSequence().flatMap { it.subFilesWithFolder }