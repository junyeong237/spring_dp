package com.example.dp.global.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JasyptConfigTest {

    @DisplayName("jasypt 암호화를 테스트한다.")
    @Test
    void jasypt() {
        // given
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(" ");
        String test = "test";

        // when
        String encryptPassword = encryptor.encrypt(test);

        // then
        Assertions.assertEquals(encryptor.decrypt(encryptPassword), "test");
    }
}