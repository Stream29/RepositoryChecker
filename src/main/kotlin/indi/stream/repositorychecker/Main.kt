package indi.stream.repositorychecker

import indi.stream.repositorychecker.api.*

fun main() {
    Students.registerStudents()
    Curriculum.registerCurriculum()

    RepositoryChecker(
        Config.getRepositoryConfig()
    ).checkNEUQACMStudents().groupBy { it.weekName }.forEach{
        println("${it.key}完成情况检查：")
        it.value.forEach{ message ->
            println("${message.studentName}：${message.message}")
        }
    }
}