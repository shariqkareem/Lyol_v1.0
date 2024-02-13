package com.shariq.lyol.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Reason {
    @Id
    @GeneratedValue
    private Integer reasonId;
    private String reason;
    private String blocker;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;
}
