package timemanagement.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import timemanagement.model.Task

@Repository
@Transactional(readOnly = true)
interface TaskRepository : CrudRepository<Task, Int> {
    fun findTaskById(id: Int): Task?

    @Transactional
    fun save(task: Task): Task

    @Query(value = "select 1 from add_subtask(?1, ?2, ?3)", nativeQuery = true)
    @Transactional
    fun addSubtask(userId: Int, taskId: Int, subtaskId: Int)

    @Query(value = "select user_contain_task(?1, ?2)", nativeQuery = true)
    @Transactional
    fun userContainTask(userId: Int, taskId: Int): Boolean

    @Query(value = "select 1 from add_task_to_label(?1, ?2, ?3)", nativeQuery = true)
    @Transactional
    fun addTaskToLabel(userId: Int, taskId: Int, labelId: Int)

    @Transactional
    override fun deleteById(taskId: Int)
}