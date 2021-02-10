package unschooler.code.managers.database.models

class Answer(
    var id: String,
    val creator: User,
    val title: String,
    val description: String?,
    val themeId: String,
    val themeName: String,
    val video: String?
) {
}