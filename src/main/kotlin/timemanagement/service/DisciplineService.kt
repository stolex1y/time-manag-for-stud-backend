package timemanagement.service

import org.springframework.transaction.annotation.Transactional
import timemanagement.dto.DisciplineDto
import timemanagement.dto.TaskDto
import timemanagement.model.Discipline
import timemanagement.model.Task

@Transactional(readOnly = true)
interface DisciplineService {
    fun getById(userId: Int, disciplineId: Int): Discipline?
    fun getAllTasks(userId: Int, disciplineId: Int): Set<Task>

    @Transactional
    fun createDisciplineTask(userId: Int, disciplineId: Int, taskDto: TaskDto): Task?

    @Transactional
    fun copyDisciplineToUser(userId: Int, disciplineId: Int): Discipline?

    @Transactional
    fun updateDiscipline(userId: Int, disciplineDto: DisciplineDto): Discipline?

    @Transactional
    fun deleteDiscipline(userId: Int, disciplineId: Int)
}