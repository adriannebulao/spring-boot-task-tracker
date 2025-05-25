package com.adriannebulao.tasktracker.userprofile.domain;

import com.adriannebulao.tasktracker.common.base.AuditableEntity;
import com.adriannebulao.tasktracker.task.domain.Task;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "\"user\"")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserProfile extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @Setter
    private String firstName;

    @Column(nullable = false)
    @Setter
    private String lastName;

    @Column(nullable = false)
    @Setter
    private String imageUrl;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
