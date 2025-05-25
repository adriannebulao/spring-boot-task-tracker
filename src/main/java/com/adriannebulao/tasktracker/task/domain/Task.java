package com.adriannebulao.tasktracker.task.domain;

import com.adriannebulao.tasktracker.common.base.AuditableEntity;
import com.adriannebulao.tasktracker.userprofile.domain.UserProfile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Task extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @Setter
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    @Setter
    private UserProfile userProfile;
}
