package spring.auth.sample.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

/**
 * @author Masoud Taghavian (masoud.taghavian@gmail.com)
 */
@Audited
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Tasks")
public class Task implements Comparable<Task>{

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String description;

    @Column
    private Boolean complete;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public int compareTo(Task o) {
        return description.compareTo(o.description);
    }
}
