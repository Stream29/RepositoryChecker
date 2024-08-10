package indi.stream.repositorychecker.api

fun RepositoryChecker.checkNEUQACMStudents() =
    checkSubfolder { studentFolder ->
        studentFolder.name.run {
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

private fun FolderChecker.specialize(
    studentName: String,
    weekName: String,
): FolderChecker =
    { file ->
        val weekFolder = file.resolve(weekName)
        (if (weekFolder.exists()) this(weekFolder) else sequenceOf("文件夹未创建"))
            .map { "${"$studentName/$weekName: "}: $it" }
    }

private val String.curriculum: String
    get() = studentToCurriculum[this] ?: throw IllegalArgumentException("未找到姓名为${this}的学生的学习路线")

private val String.requirements: Sequence<FolderChecker>
    get() = curriculumToRequirements[this.curriculum]?.asSequence()?.map { (weekName, checker) ->
        checker.specialize(this, weekName)
    } ?: throw IllegalArgumentException("未找到名为${this.curriculum}的学生的学习路线")

private val studentToCurriculum = mutableMapOf<String, String>()

private val curriculumToRequirements = mutableMapOf<String, Map<String, FolderChecker>>()


