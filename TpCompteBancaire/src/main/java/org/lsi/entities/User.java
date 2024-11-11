package org.lsi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String role;

    @OneToOne
    @JoinColumn(name = "CODE_EMP")
    private Employe employe;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}
