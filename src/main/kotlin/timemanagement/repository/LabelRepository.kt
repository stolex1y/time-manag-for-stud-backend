package timemanagement.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import timemanagement.model.Label

@Repository
@Transactional(readOnly = true)
interface LabelRepository : CrudRepository<Label, Int> {
//    fun getById(id: Int): Label?
//
//    @Transactional
//    fun save(label: Label): Label?
//    //TODO getById, getAllTasks(userId, labelId), deleteById
}