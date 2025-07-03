package com.ecommerce.coresport.configuration;

import com.ecommerce.coresport.constant.PredefinedRole;
import com.ecommerce.coresport.entity.Role;
import com.ecommerce.coresport.repository.RoleRepository;
import com.ecommerce.coresport.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.ecommerce.coresport.entity.User;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppInitConfig {

    @Value("${admin.username}")
    String adminUsername;

    @Value("${admin.email}")
    String adminEmail;

    @Value("${admin.password}")
    String adminPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "org.postgresql.Driver"
    )
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if(!userRepository.existsByUsername(adminUsername) && !userRepository.existsByEmail(adminEmail)){
                roleRepository.save(Role.builder()
                                .name(PredefinedRole.ROLE_USER)
                                .build());
                Role adminRole = roleRepository.save(
                                Role.builder().
                                        name(PredefinedRole.ROLE_ADMIN)
                                        .build()
                        );
                var roles = new HashSet<Role>();
                roles.add(adminRole);

                userRepository.save(User.builder()
                        .username(adminUsername)
                        .email(adminEmail)
                        .password(passwordEncoder.encode(adminPassword))
                        .roles(roles)
                        .enabled(true)
                        .build());
            }
        };
    }
}
