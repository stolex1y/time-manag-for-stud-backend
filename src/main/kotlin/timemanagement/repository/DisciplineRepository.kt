package timemanagement.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import timemanagement.dto.DisciplineDto
import timemanagement.model.Discipline
import java.util.*

@Repository
@Transactional(readOnly = true)
interface DisciplineRepository : CrudRepository<Discipline, Int> {
    fun findDisciplineById(id: Int): Discipline?
    fun findAllByUserLogin(login: String): Set<DisciplineDto>?

    @Transactional
    fun save(discipline: Discipline): Discipline

    @Query(value = "select 1 from add_group_task(?1, ?2, ?3, ?4)", nativeQuery = true)
    @Transactional
    fun addTaskToGroupDiscipline(userId: Int, groupId: Int, disciplineId: Int, taskId: Int)

    @Query(value = "select 1 from add_user_task(?1, ?2, ?3)", nativeQuery = true)
    @Transactional
    fun addTaskToUserDiscipline(userId: Int, disciplineId: Int, taskId: Int)

    @Query(value = "select copy_discipline_to_user(?1, ?2, ?3)", nativeQuery = true)
    @Transactional
    fun copyDisciplineToUser(userId: Int, groupId: Int, disciplineId: Int): Int?

    @Transactional
    fun deleteByIdAndUserId(disciplineId: Int, userId: Int)

    @Query(value = "select 1 from delete_group_discipline(?1, ?2, ?3)", nativeQuery = true)
    @Transactional
    fun deleteGroupDiscipline(userId: Int, groupId: Int, disciplineId: Int)
}