package cn.kr.multi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Table(name = "user2")
@Entity
public class User2 {
    @Id
    @GeneratedValue
    private UUID uuid;
    private String name;

    private String password;

    private String eamil;
}
