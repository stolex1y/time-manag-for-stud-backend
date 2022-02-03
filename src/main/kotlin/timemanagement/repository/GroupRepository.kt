package timemanagement.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import timemanagement.dto.*
import timemanagement.model.*

@Repository
@Transactional(readOnly = true)
interface GroupRepository : CrudRepository<Group, Int> {
    fun findGroupById(id: Int): Group?

    @Query(value = "select 1 from add_user_to_group(?1, ?2, ?3, ?4)", nativeQuery = true)
    @Transactional
    fun addUserToGroup(userId: Int, groupId: Int, addUserId:Int, userRight: UserRight)

    @Query(value = "select 1 from add_group_discipline(?1, ?2, ?3)", nativeQuery = true)
    @Transactional
    fun addGroupDiscipline(userId: Int, groupId: Int, disciplineId: Int)

    @Query(value = "select user_can_edit_group(?1, ?2)", nativeQuery = true)
    @Transactional
    fun userCanEditGroup(userId: Int, groupId: Int): Boolean

    @Transactional
    fun save(group: Group): Group

    @Transactional
    @Query(value = "select 1 from delete_group(?1, ?2)", nativeQuery = true)
    fun deleteById(userId: Int, groupId: Int)
}