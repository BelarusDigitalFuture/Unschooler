package unschooler.code.managers.database.models

class ThemeObject(
    var id: String,
    val subject: SubjectObject,
    val themeName: String,
    val themeDescription: String?
) {
}

class SubjectObject(val id: String, val subjectName: String)