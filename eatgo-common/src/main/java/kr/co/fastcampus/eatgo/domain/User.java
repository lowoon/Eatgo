package kr.co.fastcampus.eatgo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @Email
    private String email;

    @NotNull
    private Long level;

    public void update(Long id, String name, String email, Long level) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.level = level;
    }

    public void deactivate() {
        this.level = 0L;
    }
}
