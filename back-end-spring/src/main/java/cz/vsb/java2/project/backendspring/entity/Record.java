package cz.vsb.java2.project.backendspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "records")
@NoArgsConstructor
@Data
public class Record {
    @Id
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int score;
    @Column(name = "pointweight")
    private int pointWeight;
    @Column
    private LocalDateTime date = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
    @OneToMany(mappedBy = "record", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Map> maps;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", score=" + score +
                ", pointWeight=" + pointWeight +
                '}';
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }
}
