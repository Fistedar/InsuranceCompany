import http from 'k6/http';
import { check, sleep } from 'k6';

// Конфигурация теста
export const options = {
    stages: [
        { duration: '30s', target: 500 },  // Плавный рост до 20 пользователей
        { duration: '1m', target: 750 },   // Удержание 50 пользователей
        { duration: '20s', target: 0 },   // Завершение теста
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'], // 95% запросов должны быть < 500 мс
        http_req_failed: ['rate<0.01'],   // Допустимо 1% ошибок
    },
};

// Тестовые данные
const requestBody = {
    "agreementDateFrom" : "2026-05-25",
    "agreementDateTo" : "2026-05-29",
    "country" : "SPAIN",
    "selected_risks":["TRAVEL_MEDICAL"],
    "persons" : [
        {
            "personFirstName" : "Vasja",
            "personLastName" : "Pupkin",
            "personCode": "1232",
            "personBirthDate" : "2000-05-29",
            "medicalRiskLimitLevel": "LEVEL_10000",
            "travelCost": 4231.00
        },
        {
            "personFirstName" : "Petja",
            "personLastName" : "Pupkin",
            "personCode": "1234",
            "personBirthDate" : "1950-02-28",
            "medicalRiskLimitLevel": "LEVEL_20000",
            "travelCost": 4631.00
        }
    ]
};

const headers = {
    'Content-Type': 'application/json',
};

// Основная функция
export default function () {
    const url = 'http://localhost:8080/insurance/travel/api/v2/';
    const res = http.post(url, JSON.stringify(requestBody), { headers });

    // Проверки
    check(res, {
        'Status is 200': (r) => r.status === 200,
        'Response has premium': (r) => JSON.parse(r.body).agreementPremium !== undefined,
    });

    sleep(1); // Пауза между запросами
}