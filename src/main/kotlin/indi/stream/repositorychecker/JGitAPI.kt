package indi.stream.repositorychecker

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.TextProgressMonitor
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File
import java.io.PrintWriter

fun String.cloneRepositoryTo(path: File) {
    Git.cloneRepository()
        .setURI(this)
        .setDirectory(path)
        .setProgressMonitor(TextProgressMonitor(PrintWriter(System.out)))
        .call()
}

val File.repository
    get() = FileRepositoryBuilder()
        .setGitDir(File(this, ".git"))
        .readEnvironment()
        .findGitDir()
        .build()!!