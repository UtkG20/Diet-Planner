package com.utkarsh.dietplanner.Models;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@ToString
//@Data
@Entity
@Table(name = "client")
public class Client implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer clientId;
    private static final long serialVersionUID = 620L;
    private String username;
    private String password;
    private Integer age;



    @ManyToMany
    private List<Doctor> doctors = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList = new ArrayList<>();

    public Client(){}

    public Client(Integer age,String name, String password){
        Assert.isTrue(name != null && !"".equals(name) && password != null, "Cannot pass null or empty values to constructor");
        this.age = age;
        this.username = name;
        this.password = password;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Client) {
            Client user = (Client)obj;
            return this.username.equals(user.getUsername());
        } else {
            return false;
        }
    }

    public static Client.UserBuilder withUsername(String username) {
        return builder().username(username);
    }

    public static Client.UserBuilder builder() {
        return new Client.UserBuilder();
    }

    public static Client.UserBuilder withUserDetails(Client userDetails) {
        return withUsername(userDetails.getUsername()).password(userDetails.getPassword()).age(userDetails.getAge());
    }

    public Integer getClientId() {
        return this.clientId;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = 620L;

        private AuthorityComparator() {
        }

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            } else {
                return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
            }
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {return Collections.emptyList();}

    @Override
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password){this.password = password;}

    @Override
    public String getUsername() {return this.username;}
    public void setUsername(String username){this.username = username;}

    public Integer getAge(){return this.age;}
    public void setAge(Integer age){this.age = age;}

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(" [");
        sb.append("Username=").append(this.username).append(", ");
        sb.append("Password=[PROTECTED], ");
        sb.append("Age=").append(this.age).append(", ");
        return sb.toString();
    }


    public static final class UserBuilder {
        private String username;
        private String password;
        private Integer age;
        private Function<String, String> passwordEncoder = (password) -> {
            return password;
        };

        private UserBuilder() {
        }

        public Client.UserBuilder username(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        public Client.UserBuilder password(String password) {
            Assert.notNull(password, "password cannot be null");
            this.password = password;
            return this;
        }

        public Client.UserBuilder age(Integer age){
            Assert.notNull(age,"age cannot be null");
            this.age = age;
            return this;
        }

        public Client.UserBuilder passwordEncoder(Function<String, String> encoder) {
            Assert.notNull(encoder, "encoder cannot be null");
            this.passwordEncoder = encoder;
            return this;
        }


        public UserDetails build() {
            String encodedPassword = this.passwordEncoder.apply(this.password);
            return new Client(this.age, this.username, encodedPassword);
        }
    }
}
