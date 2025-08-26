CREATE SCHEMA IF NOT EXISTS `java-real-practice-insurance`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE `java-real-practice-insurance`;

CREATE TABLE IF NOT EXISTS `classifiers` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(200) NOT NULL,
    `description` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_classifiers_title` (`title`)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `classifier_values` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `classifier_id` BIGINT NOT NULL,
    `ic` VARCHAR(200) NOT NULL,
    `description` VARCHAR(500) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_classifier_values_ic` (`ic`),
    CONSTRAINT `fk_classifier_values_classifier`
    FOREIGN KEY (`classifier_id`)
    REFERENCES `classifiers` (`id`)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `country_default_day_rate` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `classifier_value_id` BIGINT NOT NULL,  -- Ссылка на запись в classifier_values
    `default_day_rate` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_country_rate_classifier_value` (`classifier_value_id`),
    CONSTRAINT `fk_country_rate_classifier_value`
    FOREIGN KEY (`classifier_value_id`)
    REFERENCES `classifier_values` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS age_coefficient (
    id BIGINT NOT NULL AUTO_INCREMENT,
    age_from INT NOT NULL,
    age_to INT NOT NULL,
    coefficient DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS medical_risk_limit_level(
    id BIGINT NOT NULL AUTO_INCREMENT,
    classifier_value_id BIGINT NOT NULL,
    coefficient DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT classifier_value_id_fk
    FOREIGN KEY (classifier_value_id)
    REFERENCES classifier_values (id)
)   ENGINE=InnoDB;
