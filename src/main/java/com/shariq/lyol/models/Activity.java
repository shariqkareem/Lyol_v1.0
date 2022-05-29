package com.shariq.lyol.models;

import com.shariq.lyol.enums.ActivityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Activity {
    @Id
    @GeneratedValue
    private int id;
    private String activity;
    private String lifeSection;
    private boolean isImportant;
    private LocalTime startTime;
    private LocalTime endTime;
    private ActivityStatus activityStatus;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<Reason> reasonsForNotCompleting;
}
