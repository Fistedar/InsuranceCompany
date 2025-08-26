-- Добавляем классификатор типов рисков
INSERT INTO classifiers(title, description)
VALUES('RISK_TYPE', 'Risk type classifier');

-- Добавляем значения для классификатора RISK_TYPE
INSERT INTO classifier_values(classifier_id, ic, description)
SELECT
    cl.id,
    vals.ic,
    vals.description
FROM classifiers as cl
         CROSS JOIN (
    VALUES
        ('TRAVEL_MEDICAL', 'Travel policy medical risk type'),
        ('TRAVEL_CANCELLATION', 'Travel policy trip cancellation risk type'),
        ('TRAVEL_LOSS_BAGGAGE', 'Travel policy baggage lose risk type'),
        ('TRAVEL_THIRD_PARTY_LIABILITY', 'Travel policy third party liability risk type'),
        ('TRAVEL_EVACUATION', 'Travel policy evacuation risk type'),
        ('TRAVEL_SPORT_ACTIVITIES', 'Travel policy sport activities risk type')
) AS vals(ic, description)
WHERE cl.title = 'RISK_TYPE';

INSERT INTO classifiers(title, description)
VALUES('COUNTRY','country classifier');

INSERT INTO classifier_values(classifier_id, ic, description)
SELECT
    cl.id,
    vals.ic,
    vals.description
FROM classifiers as cl
         CROSS JOIN (
    VALUES
        ('LATVIA', 'Country Latvia'),
        ('SPAIN','Country Spain'),
        ('JAPAN', 'Country Japan')
) AS vals (ic,description)
WHERE cl.title = 'COUNTRY';

INSERT INTO country_default_day_rate(classifier_value_id, default_day_rate)
select
    cv.id,
    CASE
        WHEN cv.ic = 'SPAIN' THEN 2.50
        WHEN cv.ic = 'JAPAN' THEN 3.50
        WHEN cv.ic = 'LATVIA' THEN 1.00
        END
from classifier_values cv
WHERE cv.ic IN ('SPAIN', 'JAPAN', 'LATVIA');

INSERT INTO age_coefficient(age_from, age_to, coefficient)
VALUES  (0, 5, 1.1),
        (6, 10, 0.7),
        (11, 17, 1.0),
        (18, 40, 1.1),
        (41, 65, 1.2),
        (66, 150, 1.5);

INSERT INTO classifiers(title, description)
VALUES ('MEDICAL_RISK_LIMIT_LEVEL','Medical Risk limit level classifier');

INSERT INTO classifier_values(classifier_id, ic, description)
SELECT cl.id,
       vals.ic,
       vals.description
from classifiers as cl cross join (
    SELECT 'LEVEL_10000' as ic ,'Medical Risk 10000 euro limit level' as description union all
    SELECT 'LEVEL_15000','Medical Risk 15000 euro limit level' union all
    SELECT 'LEVEL_20000','Medical Risk 20000 euro limit level' union all
    SELECT 'LEVEL_50000','Medical Risk 50000 euro limit level'
) as vals(ic,description)
where cl.title = 'MEDICAL_RISK_LIMIT_LEVEL';

INSERT INTO medical_risk_limit_level(classifier_value_id, coefficient)
SELECT
    cv.id,
    CASE
        WHEN cv.ic = 'LEVEL_10000' THEN 1.0
        WHEN cv.ic = 'LEVEL_15000' THEN 1.2
        WHEN cv.ic = 'LEVEL_20000' THEN 1.5
        WHEN cv.ic = 'LEVEL_50000' THEN 2.0
        END
FROM classifier_values cv
Where cv.ic IN('LEVEL_10000','LEVEL_15000','LEVEL_20000','LEVEL_50000');