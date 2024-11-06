package com.tacos.taco_cloud;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.tacos.taco_cloud.data.*;
import com.tacos.taco_cloud.models.*;

class UserTest {

    @Test
    void testUserDetailsImplementation() {
        // Arrange: создаем пользователя
        User user = new User("testUser", "password", "John Doe", "123 Street", "City", "State", "12345", "555-1234");

        // Act & Assert: проверяем поля безопасности
        // assertThat(user.getAuthorities()).containsExactly(new
        // SimpleGrantedAuthority("ROLE_USER"));
        assertThat(user.isAccountNonExpired()).isTrue();
        assertThat(user.isAccountNonLocked()).isTrue();
        assertThat(user.isCredentialsNonExpired()).isTrue();
        assertThat(user.isEnabled()).isTrue();
    }
}
