package timemanagement.dto

import timemanagement.model.Discipline
import timemanagement.model.Group
import timemanagement.model.State

data class DisciplineDto(
    var id: Int? = null,
    var name: String,
    var comment: String? = null,
    var state: State = State.WORK,
    var priority: Int? = null,
) {
    constructor(d: Discipline) :
            this(d.id, d.name, d.comment, d.state, d.priority)

    companion object {
        fun toDisciplineDto(groups: Set<Discipline>): Set<DisciplineDto> {
            val result: MutableList<DisciplineDto> = mutableListOf()
            groups.forEach {
                result.add(DisciplineDto(it))
            }
            return result.toSet()
        }
    }

    fun toDiscipline(): Discipline {
        return Discipline(
            id = this.id,
            name = this.name,
            comment = this.comment,
            state = this.state,
            priority = this.priority
        )
    }

    fun updateDiscipline(discipline: Discipline) {
        require(discipline.id == this.id)
        discipline.name = this.name
        discipline.comment = this.comment
        discipline.state = this.state
        discipline.priority = this.priority
    }
}