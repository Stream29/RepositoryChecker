package indi.stream.repositorychecker.api

import java.io.File

fun RepositoryChecker.checkNEUQACMStudents() =
    checkSubfolder { studentFolder ->
        if (studentToCurriculum[studentFolder.name] == null) emptySequence()
        else studentFolder.name.run {
            requirements.map {
                it(studentFolder)
            }.flatten()
        }
    }

fun registerCurriculumWithStudent(curriculum: String, students: List<String>) {
    students.forEach { studentToCurriculum[it] = curriculum }
}

fun registerCurriculumWithRequirements(curriculum: String, requirements: Map<String, FolderChecker>) {
    curriculumToRequirements[curriculum] = requirements
}

data class CheckMessage(
    val studentName: String,
    val weekName: String,
    val message: String,
)

typealias SpecializedChecker = (File) -> Sequence<CheckMessage>

private fun FolderChecker.specialize(
    studentName: String,
    weekName: String,
): SpecializedChecker =
    { file ->
        val weekFolder = file.resolve(weekName)
        (if (weekFolder.exists()) this(weekFolder) else sequenceOf("文件夹未创建"))
            .ifEmpty { sequenceOf("没有问题") }
            .map{ CheckMessage(studentName, weekName, it) }
    }

private val String.curriculum: String
    get() = studentToCurriculum[this] ?: throw IllegalArgumentException("未找到姓名为${this}的学生的学习路线")

private val String.requirements: Sequence<SpecializedChecker>
    get() = curriculumToRequirements[this.curriculum]?.asSequence()?.map { (weekName, checker) ->
        checker.specialize(this, weekName)
    } ?: throw IllegalArgumentException("未找到名为${this.curriculum}的学生的学习路线")

private val studentToCurriculum = mutableMapOf<String, String>()

private val curriculumToRequirements = mutableMapOf<String, Map<String, FolderChecker>>()


