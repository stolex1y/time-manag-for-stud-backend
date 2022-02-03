package timemanagement.dto

import timemanagement.model.Group

data class GroupDto(
    var id: Int? = null,
    var name: String
) {
    constructor(group: Group) : this(group.id, group.name)

    companion object {
        fun toGroupDto(groups: Set<Group>): Set<GroupDto> {
            val result: MutableList<GroupDto> = mutableListOf()
            groups.forEach {
                result.add(GroupDto(it))
            }
            return result.toSet()
        }
    }

    fun toGroup(): Group {
        return Group(
            id = this.id,
            name = this.name
        )
    }

    fun updateGroup(group: Group) {
        require(group.id == this.id)
        group.name = this.name
    }
}