package timemanagement.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "Дисциплины")
class Discipline(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ИД")
    var id: Int? = null,

    @Column(name = "Название")
    var name: String,

    @Column(name = "Комментарий")
    var comment: String? = null,

    @Column(name = "Состояние")
    @Enumerated(value = EnumType.STRING)
    var state: State = State.WORK,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "discipline", fetch = FetchType.LAZY)
    @JsonManagedReference
    var tasks: Set<Task> = setOf(),

    @ManyToOne
    @JoinTable(
        name = "Дисциплины_группа",
        joinColumns = [JoinColumn(name = "Дисциплина_ИД")],
        inverseJoinColumns = [JoinColumn(name = "Группа_ИД")]
    )
    @JsonBackReference
    var group: Group? = null,

    @ManyToOne
    @JoinTable(
        name = "Дисциплины_пользователи",
        joinColumns = [JoinColumn(name = "Дисциплина_ИД")],
        inverseJoinColumns = [JoinColumn(name = "Пользователь_ИД")]
    )
    @JsonBackReference
    var user: User? = null,

    @Column(name = "Приоритет")
    var priority: Int? = null
) {
    fun isUserDiscipline() = user != null
    fun isGroupDiscipline() = group != null
}
