package com.utkarsh.dietplanner.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@Entity(name="users")

public class ExperimentCustomUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userId;

    private static final long serialVersionUID = 620L;
    private static final Log logger = LogFactory.getLog(User.class);
    private String password;
    private String username;
    private Integer age;
//    private final Set<GrantedAuthority> authorities;
//    private final boolean accountNonExpired;
//    private final boolean accountNonLocked;
//    private final boolean credentialsNonExpired;
//    private final boolean enabled;


//    public ExperimentCustomUser(String username, String password,Integer age) {
//        this(username, password, age);
//    }

    public ExperimentCustomUser(){}

    public ExperimentCustomUser(String username, String password, Integer age) {
        Assert.isTrue(username != null && !"".equals(username) && password != null, "Cannot pass null or empty values to constructor");
        this.username = username;
        this.password = password;
        this.age = age;
//        this.enabled = enabled;
//        this.accountNonExpired = accountNonExpired;
//        this.credentialsNonExpired = credentialsNonExpired;
//        this.accountNonLocked = accountNonLocked;
//        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Integer getAge(){ return  this.age; }

    @Override
    public boolean isAccountNonExpired() { return true;}

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {
        return true;
    }

//    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
//        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
//        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet(new ExperimentCustomUser.AuthorityComparator());
//        Iterator var2 = authorities.iterator();
//
//        while(var2.hasNext()) {
//            GrantedAuthority grantedAuthority = (GrantedAuthority)var2.next();
//            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
//            sortedAuthorities.add(grantedAuthority);
//        }
//
//        return sortedAuthorities;
//    }

    public boolean equals(Object obj) {
        if (obj instanceof ExperimentCustomUser) {
            ExperimentCustomUser user = (ExperimentCustomUser)obj;
            return this.username.equals(user.getUsername());
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(" [");
        sb.append("Username=").append(this.username).append(", ");
        sb.append("Password=[PROTECTED], ");
        sb.append("Age=").append(this.age).append(", ");
//        sb.append("Enabled=").append(this.enabled).append(", ");
//        sb.append("AccountNonExpired=").append(this.accountNonExpired).append(", ");
//        sb.append("CredentialsNonExpired=").append(this.credentialsNonExpired).append(", ");
//        sb.append("AccountNonLocked=").append(this.accountNonLocked).append(", ");
//        sb.append("Granted Authorities=").append(this.authorities).append("]");
        return sb.toString();
    }

    public static ExperimentCustomUser.UserBuilder withUsername(String username) {
        return builder().username(username);
    }

    public static ExperimentCustomUser.UserBuilder builder() {
        return new ExperimentCustomUser.UserBuilder();
    }

    public static ExperimentCustomUser.UserBuilder withUserDetails(ExperimentCustomUser userDetails) {
//        return withUsername(userDetails.getUsername()).password(userDetails.getPassword()).age(userDetails.getAge()).accountExpired(!userDetails.isAccountNonExpired()).accountLocked(!userDetails.isAccountNonLocked()).authorities(userDetails.getAuthorities()).credentialsExpired(!userDetails.isCredentialsNonExpired()).disabled(!userDetails.isEnabled());
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

    public static final class UserBuilder {
        private String username;
        private String password;
        private Integer age;
//        private List<GrantedAuthority> authorities = new ArrayList();
//        private boolean accountExpired;
//        private boolean accountLocked;
//        private boolean credentialsExpired;
//        private boolean disabled;
        private Function<String, String> passwordEncoder = (password) -> {
            return password;
        };

        private UserBuilder() {
        }

        public ExperimentCustomUser.UserBuilder username(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        public ExperimentCustomUser.UserBuilder password(String password) {
            Assert.notNull(password, "password cannot be null");
            this.password = password;
            return this;
        }

        public ExperimentCustomUser.UserBuilder age(Integer age){
            Assert.notNull(age,"age cannot be null");
            this.age = age;
            return this;
        }

        public ExperimentCustomUser.UserBuilder passwordEncoder(Function<String, String> encoder) {
            Assert.notNull(encoder, "encoder cannot be null");
            this.passwordEncoder = encoder;
            return this;
        }

//        public ExperimentCustomUser.UserBuilder roles(String... roles) {
//            List<GrantedAuthority> authorities = new ArrayList(roles.length);
//            String[] var3 = roles;
//            int var4 = roles.length;
//
//            for(int var5 = 0; var5 < var4; ++var5) {
//                String role = var3[var5];
//                Assert.isTrue(!role.startsWith("ROLE_"), () -> {
//                    return role + " cannot start with ROLE_ (it is automatically added)";
//                });
//                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//            }
//
//            return this.authorities((Collection)authorities);
//        }

//        public ExperimentCustomUser.UserBuilder authorities(GrantedAuthority... authorities) {
//            Assert.notNull(authorities, "authorities cannot be null");
//            return this.authorities((Collection)Arrays.asList(authorities));
//        }

//        public ExperimentCustomUser.UserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
//            Assert.notNull(authorities, "authorities cannot be null");
//            this.authorities = new ArrayList(authorities);
//            return this;
//        }

//        public ExperimentCustomUser.UserBuilder authorities(String... authorities) {
//            Assert.notNull(authorities, "authorities cannot be null");
//            return this.authorities((Collection) AuthorityUtils.createAuthorityList(authorities));
//        }

//        public ExperimentCustomUser.UserBuilder accountExpired(boolean accountExpired) {
//            this.accountExpired = accountExpired;
//            return this;
//        }

//        public ExperimentCustomUser.UserBuilder accountLocked(boolean accountLocked) {
//            this.accountLocked = accountLocked;
//            return this;
//        }

//        public ExperimentCustomUser.UserBuilder credentialsExpired(boolean credentialsExpired) {
//            this.credentialsExpired = credentialsExpired;
//            return this;
//        }

//        public ExperimentCustomUser.UserBuilder disabled(boolean disabled) {
//            this.disabled = disabled;
//            return this;
//        }

        public UserDetails build() {
            String encodedPassword = (String)this.passwordEncoder.apply(this.password);
            return new ExperimentCustomUser(this.username, encodedPassword, this.age);
        }
    }
}
