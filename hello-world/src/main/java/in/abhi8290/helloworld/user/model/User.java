package in.abhi8290.helloworld.user.model;

import in.abhi8290.helloworld.core.base.BaseEntity;
import in.abhi8290.helloworld.core.exception.common.InvalidParamsProvidedException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {



    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;


    @Column(nullable = false)
    private String password;

    @Email(message = "Invalid Email ")
    @Column(unique = true, nullable = false)
    private String email;




    @Column( nullable = false)
    private boolean isEmailVerified;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    public User() {}

    public User(String firstName, String lastName, String email, String password,  AuthProvider provider) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.provider = provider;


        if(provider == AuthProvider.GOOGLE) {
            this.isEmailVerified = true;
        }
        if(provider== AuthProvider.GITHUB){
            // This is wrong as You need to call github's api for this
            this.isEmailVerified = true;
        }

        setEmail(email);
        setPassword(password);
    }


    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        if(password.length() < 8) {
            throw new InvalidParamsProvidedException("Password must be at least 8 characters");
        }
        this.password = password;
    }
    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public void setEmailVerified(boolean emailVerified) {this.isEmailVerified = emailVerified;}

    public void setEmail(String email) {

        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new InvalidParamsProvidedException("Baba Email Provided ");
        }
        this.email = email;

    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + role.name()) // e.g., ROLE_USER or ROLE_ADMIN
        );
    }

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private final Role role = Role.USER;

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
