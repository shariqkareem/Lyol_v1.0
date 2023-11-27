package com.shariq.lyol.models;

import com.shariq.lyol.enums.ActivityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Activity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String activity;
    private String lifeSection;
    private boolean isImportant;
    private LocalTime startTime;
    private LocalTime endTime;
    private ActivityStatus activityStatus;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reason> reasonsForNotCompleting;
}
