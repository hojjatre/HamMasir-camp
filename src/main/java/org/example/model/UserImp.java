package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

//@RedisHash("UserImp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserImp implements Serializable {
    private Integer id;
    private String username;
    private String email;
}
