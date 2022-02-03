package timemanagement.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "Пользователи")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ИД")
    var id: Int? = null,

    @Column(name = "Имя_пользователя")
    var login: String,

    @Column(name = "Пароль")
    @JsonIgnore
    var pass: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "Группы_пользователи",
        joinColumns = [JoinColumn(name = "Пользователь_ИД")],
        inverseJoinColumns = [JoinColumn(name = "Группа_ИД")]
    )
    @JsonManagedReference
    var groups: Set<Group> = setOf(),

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "Дисциплины_пользователи",
        joinColumns = [JoinColumn(name = "Пользователь_ИД")],
        inverseJoinColumns = [JoinColumn(name = "Дисциплина_ИД")],
    )
    @JsonManagedReference
    var disciplines: Set<Discipline> = setOf()
)
