package timemanagement.service

import org.springframework.transaction.annotation.Transactional
import timemanagement.model.Label

@Transactional(readOnly = true)
interface LabelService {
//    fun getById(labelId: Int)
//    fun getAllUserLabeledTask(userId: Int, labelId: Int): Set<Label>
//    fun getAllGroupLabeledTask(groupId: Int, labelId: Int): Set<Label>

}