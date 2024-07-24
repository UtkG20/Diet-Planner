package com.utkarsh.dietplanner.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.print.Doc;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;


@Entity
@Table(name = "Doctor", uniqueConstraints = {@UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = {"docId", "clientId"})})
public class Doctor  implements UserDetails {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer docId;
    private static final long serialVersionUID = 620L;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;
    private Integer age;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    private List<Meal> mealList = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(
//            mappedBy = "doctors",
            cascade = CascadeType.ALL)
    @JoinTable(name = "doctorClientMapping",
            joinColumns = @JoinColumn(name = "docId"),
            inverseJoinColumns = @JoinColumn(name = "clientId"),uniqueConstraints = @UniqueConstraint(columnNames = {"docId","clientId"}))
    private List<Client> clientList = new ArrayList<>();

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor(){}

    public Doctor(String username, String password, Integer age){
        Assert.isTrue(username != null && !"".equals(username) && password != null && age != null, "Cannot pass null or empty values to constructor");
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public Integer getDocId() {
        return this.docId;
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

    @JsonIgnore
    public List<Client> getClientList(){
        return this.clientList;
    }

    @JsonIgnore
    public List<Meal> getMealList(){
        return this.mealList;
    }

    @JsonIgnore
    public List<Appointment> getAppointments(){return this.appointments;}

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {return true;}

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {return true;}

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @JsonIgnore
    @Override
    public boolean isEnabled() {return true;}

    public boolean equals(Object obj) {
        if (obj instanceof Doctor) {
            Doctor user = (Doctor)obj;
            return this.username.equals(user.getUsername());
        } else {
            return false;
        }
    }

    public static Doctor.UserBuilder withUsername(String username) {
        return builder().username(username);
    }

    public static Doctor.UserBuilder builder() {
        return new Doctor.UserBuilder();
    }

    public static Doctor.UserBuilder withUserDetails(Client userDetails) {
        return withUsername(userDetails.getUsername()).password(userDetails.getPassword()).age(userDetails.getAge());
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

        public Doctor.UserBuilder username(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        public Doctor.UserBuilder password(String password) {
            Assert.notNull(password, "password cannot be null");
            this.password = password;
            return this;
        }

        public Doctor.UserBuilder age(Integer age){
            Assert.notNull(age,"age cannot be null");
            this.age = age;
            return this;
        }

        public Doctor.UserBuilder passwordEncoder(Function<String, String> encoder) {
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
