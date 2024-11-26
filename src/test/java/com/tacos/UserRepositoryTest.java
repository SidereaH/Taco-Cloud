package com.tacos;

import static org.assertj.core.api.Assertions.assertThat;

import com.tacos.data.UserRepository;
import com.tacos.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        // Arrange: создаем и сохраняем пользователя в базе
        User user = new User("testUser", "password", "John Doe", "123 Street", "City", "State", "12345", "555-1234");
        userRepository.save(user);

        // Act: получаем пользователя по username
        User foundUser = userRepository.findByUsername("testUser");

        // Assert: проверяем, что пользователь найден и данные совпадают
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testUser");
        assertThat(foundUser.getFullname()).isEqualTo("John Doe");
        assertThat(foundUser.getPhoneNumber()).isEqualTo("555-1234");
    }
}
