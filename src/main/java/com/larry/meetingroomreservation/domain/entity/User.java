package com.larry.meetingroomreservation.domain.entity;

import com.larry.meetingroomreservation.domain.entity.support.AbstractEntity;
import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends AbstractEntity{

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Column
    private String socialId;

    @Column
    private String profileHref;

    @Enumerated(value = EnumType.STRING)
    private SocialProviders socialProvider;
}
