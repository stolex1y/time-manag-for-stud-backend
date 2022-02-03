package timemanagement.model

enum class UserRight {
    EDIT,
    VIEW;

    companion object {
        fun valueOfIgnoreCase(value: String): UserRight = valueOf(value.uppercase())
    }
}