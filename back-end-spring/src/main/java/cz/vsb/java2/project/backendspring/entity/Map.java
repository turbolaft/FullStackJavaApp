package cz.vsb.java2.project.backendspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "maps")
@NoArgsConstructor
@Data
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_id")
    private Long id;

    @Column(nullable = false, name = "map_level")
    private int mapLevel;

    @Column(nullable = false, name = "map_before")
    private String mapBefore;

    @Column(nullable = false, name = "map_after")
    private String mapAfter;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    public Map(int mapLevel, String mapBefore, String mapAfter) {
        this.mapLevel = mapLevel;
        this.mapBefore = mapBefore;
        this.mapAfter = mapAfter;
    }

    @JsonIgnore
    public Record getRecord() {
        return record;
    }
}
