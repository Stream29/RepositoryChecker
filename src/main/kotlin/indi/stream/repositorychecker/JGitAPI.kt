package indi.stream.repositorychecker

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.TextProgressMonitor
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.SshSessionFactory
import org.eclipse.jgit.transport.sshd.JGitKeyCache
import org.eclipse.jgit.transport.sshd.SshdSessionFactoryBuilder
import org.eclipse.jgit.util.FS
import java.io.File
import java.io.OutputStreamWriter

fun sshInit() {
    SshSessionFactory.setInstance(
        SshdSessionFactoryBuilder()
            .setPreferredAuthentications("publickey,keyboard-interactive,password")
            .setHomeDirectory(FS.DETECTED.userHome())
            .setSshDirectory(FS.DETECTED.userHome().resolve(".ssh")).build(JGitKeyCache())
    )
}

fun String.cloneRepositoryTo(path: File) {
    Git.cloneRepository()
        .setURI(this)
        .setDirectory(path)
        .setProgressMonitor(textProgressMonitor)
        .call()
}

val File.repository
    get() = FileRepositoryBuilder()
        .setGitDir(File(this, ".git"))
        .readEnvironment()
        .findGitDir()
        .build()!!

private val textProgressMonitor = TextProgressMonitor(OutputStreamWriter(System.out))
