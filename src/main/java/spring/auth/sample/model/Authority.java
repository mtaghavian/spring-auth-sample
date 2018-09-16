package spring.auth.sample.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "Authorities") // because a table with this class name may exist (reserved names)
public class Authority {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String name;
}
