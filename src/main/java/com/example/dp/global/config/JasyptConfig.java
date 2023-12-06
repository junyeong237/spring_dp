package com.example.dp.global.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    private static final String PASSWORD = "DB_DECRYPT_KEY";
    private static final String ALGORITHM = "PBEWithMD5AndDES";
    private static final String KEY_OBJECTION_ITERATIONS = "1000";
    private static final String POOL_SIZE = "1";
    private static final String PROVIDER_NAME = "SunJCE";
    private static final String SALT_GENERATOR_CLASS_NAME = "org.jasypt.salt.RandomSaltGenerator";
    private static final String STRING_OUTPUT_TYPE = "base64";

    @Bean("jasyptStringEncryptor")
    StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(System.getenv(PASSWORD));
        config.setAlgorithm(ALGORITHM);
        config.setKeyObtentionIterations(KEY_OBJECTION_ITERATIONS);
        config.setPoolSize(POOL_SIZE);
        config.setProviderName(PROVIDER_NAME);
        config.setSaltGeneratorClassName(SALT_GENERATOR_CLASS_NAME);
        config.setStringOutputType(STRING_OUTPUT_TYPE);
        encryptor.setConfig(config);

        return encryptor;
    }
}
