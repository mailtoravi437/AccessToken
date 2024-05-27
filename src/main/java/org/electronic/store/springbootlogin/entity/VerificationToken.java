package org.electronic.store.springbootlogin.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class VerificationToken {
    private static final int EXPIRATION = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "user_id")
    private User user;

    public VerificationToken(User user, String token) {
        super();
        this.user = user;
        this.token = token;
        this.expirationTime = calculateExpirationTime();
    }

    public VerificationToken(String token){
        super();
        this.token = token;
        this.expirationTime = calculateExpirationTime();
    }

    public Date calculateExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,EXPIRATION);
        return new Date(calendar.getTime().getTime());
    }


}
