package timemanagement.model

enum class TaskType {
    TABLE,
    LIST;

    companion object {
        fun valueOfIgnoreCase(value: String): TaskType = valueOf(value.uppercase())
    }
}