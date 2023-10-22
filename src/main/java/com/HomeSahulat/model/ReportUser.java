package com.HomeSahulat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "report_user")
public class ReportUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String note;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "report_from_user_id")
    private User reportFromUser;

    @ManyToOne
    @JoinColumn(name = "report_to_user_id")
    private User reportToUser;
}
