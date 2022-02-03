package timemanagement.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Задачи")
class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ИД")
    var id: Int? = null,

    @Column(name = "Название")
    var name: String,

    @ManyToOne
    @JoinColumn(name = "Дисциплина_ИД")
    @JsonBackReference
    var discipline: Discipline? = null,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "parentTask", fetch = FetchType.LAZY)
    @JsonManagedReference
    val subtasks: Set<Task> = setOf(),

    @ManyToOne
    @JoinColumn(name = "Род_задача_ИД")
    @JsonBackReference
    val parentTask: Task? = null,

    @Column(name = "Дедлайн")
    var deadline: Calendar,

    @Column(name = "Комментарий")
    var comment: String? = null,

    @Column(name = "Состояние")
    @Enumerated(value = EnumType.STRING)
    var state: State = State.WORK,

    @Column(name = "Вид")
    @Enumerated(value = EnumType.STRING)
    var taskType: TaskType? = null,

    @ManyToMany
    @JoinTable(
        name = "Метки_задачи",
        joinColumns = [JoinColumn(name = "Задача_ИД")],
        inverseJoinColumns = [JoinColumn(name = "Метка_ИД")]
    )
    @JsonBackReference
    var labels: Set<Label> = setOf(),

    @Column(name = "Приоритет")
    var priority: Int? = null
) {
    fun isDisciplineTask() = discipline != null
    fun isSubtask() = parentTask != null

    fun getRootTask(): Task {
        var task: Task = this
        while (task.parentTask != null)
            task = task.parentTask!!
        return task
    }

    fun getRootDiscipline() = getRootTask().discipline!!

    fun isUserTask() = getRootDiscipline().isUserDiscipline()
    fun isGroupTask() = getRootDiscipline().isGroupDiscipline()
}
