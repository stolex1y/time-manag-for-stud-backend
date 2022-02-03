package timemanagement.model

import java.util.*

enum class State {
    WORK,
    DONE;

    companion object {
        fun valueOfIgnoreCase(value: String): State = valueOf(value.uppercase())
    }
}