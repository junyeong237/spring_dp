package com.example.dp.domain.admin.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.dp.domain.admin.service.AdminUserService;
import com.example.dp.domain.user.dto.response.UserResponseDto;
import com.example.dp.domain.user.entity.User;
import com.example.dp.domain.user.repository.UserRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AdminUserServiceImplTest {

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    UserRepository userRepository;
    FixtureMonkey fixtureMonkey;

    @BeforeEach
    void setup() {
        fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();
    }

    @Nested
    @DisplayName("사용자 조회 테스트")
    class ReadUserTest {

        @Test
        @DisplayName("사용자 단건 조회")
        void readUser() {
            // given
            User user = createAndSaveUser();

            // when
            UserResponseDto responseDto = adminUserService.getUser(user.getId());

            // then
            assertThat(responseDto.getId()).isEqualTo(user.getId());
        }

        @Transactional(propagation = Propagation.NEVER)
        @Test
        @DisplayName("사용자 전체 조회")
        @Disabled
        void readAllUser() {
            // given
            int count = 5;

            for (int i = 0; i < count; i++) {
                createAndSaveUser();
            }

            // when
            List<UserResponseDto> responseDto = adminUserService.getAllUsers();

            // then
            assertThat(responseDto.size()).isEqualTo(count);
        }
    }

    private User createAndSaveUser() {
        User sample = fixtureMonkey.giveMeBuilder(User.class)
            .setNotNull("*")
            .setNull("id")
            .sample();
        return userRepository.save(sample);
    }

}