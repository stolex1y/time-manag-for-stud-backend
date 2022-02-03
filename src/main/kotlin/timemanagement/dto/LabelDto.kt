package timemanagement.dto

import timemanagement.model.Label
import timemanagement.model.Task

data class LabelDto(
    var id: Int? = null,
    var name: String,
    var comment: String? = null
) {
    companion object {
        fun toLabelDto(groups: Set<Label>): Set<LabelDto> {
            val result: MutableList<LabelDto> = mutableListOf()
            groups.forEach {
                result.add(LabelDto(it))
            }
            return result.toSet()
        }
    }
    
    constructor(label: Label) :
            this(label.id, label.name, label.comment)

    fun toLabel(): Label {
        return Label(
            id = this.id,
            name = this.name,
            comment = this.comment
        )
    }
}