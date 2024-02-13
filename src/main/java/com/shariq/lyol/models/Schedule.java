package com.shariq.lyol.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Schedule {
    @Id
    @Builder.Default
    private LocalDate date = LocalDate.now();
    @OneToMany(mappedBy = "schedule",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Builder.Default
    private List<Activity> activities = new ArrayList<>();
    private float score;
}
