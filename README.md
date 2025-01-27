# Currency Rate Converter

![Currency Rate Converter](https://img.shields.io/badge/SpringBoot-2.7.5-brightgreen)
![Kotlin](https://img.shields.io/badge/Kotlin-1.6.21-blue)

**Currency Rate Converter** is a Spring Boot application written in Java and Kotlin that provides RESTful APIs to fetch fiat and cryptocurrency rates and perform conversions between any two supported currencies. It integrates with external APIs like [CoinPaprika](https://coinpaprika.com/) for cryptocurrency data and [Free Currency API](https://freecurrencyapi.com/) for fiat currency rates.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
    - [Get Fiat Currency Rates](#get-fiat-currency-rates)
    - [Get All Cryptocurrency Rates](#get-all-cryptocurrency-rates)
    - [Get Cryptocurrency Rate by ID](#get-cryptocurrency-rate-by-id)
    - [Convert Between Currencies](#convert-between-currencies)
- [Usage Examples](#usage-examples)
- [Error Handling](#error-handling)

## Features

- **Fetch Fiat Currency Rates**: Retrieve up-to-date fiat currency rates against USD.
- **Fetch Cryptocurrency Rates**: Retrieve current cryptocurrency rates against USD from CoinPaprika.
- **Currency Conversion**: Convert an amount from any supported currency (fiat or crypto) to any other supported currency.
- **Extensible**: Easily add support for more currencies or integrate additional data sources.

## Technologies Used

- **Kotlin**: JVM language for data classes and expressive code.
- **Java**: Main language
- **Spring Boot**: Framework for building standalone, production-grade Spring applications.
- **Spring WebFlux**: Reactive web framework for handling asynchronous requests.
- **WebClient**: Reactive HTTP client for making API calls.
- **Jackson**: JSON processing library for serialization/deserialization.
- **Maven**: Build automation tool.

## Prerequisites

- **Java 23** or higher

## Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/stependiant/currency-converter.git
   cd currency-converter
   ```


## Configuration

The application requires API keys and other configurations to function correctly. These settings are specified in the `application.yml` file.

### `application.yml`

```yaml
spring:
  application:
    name: currency-rate

freecurrencyapi:
  key: YOUR_FREECURRENCYAPI_KEY_HERE

server:
  port: 9191
```

### Configuration Details

- **`spring.codec.max-in-memory-size`**: Increases the maximum size of data that can be buffered in memory. Set to `10MB` to handle large responses from CoinPaprika.
- **`freecurrencyapi.key`**: Your API key for [Free Currency API](https://freecurrencyapi.com/). Replace `YOUR_FREECURRENCYAPI_KEY_HERE` with your actual key.
- **`server.port`**: Port on which the application runs. Default is `9191`.

### Obtaining API Keys

- **Free Currency API**:
    - Visit [Free Currency API](https://freecurrencyapi.com/) and sign up to obtain your API key.

- **CoinPaprika**:
    - No API key is required for basic endpoints like `/v1/tickers`.


The application will start on `http://localhost:9191`.

## API Endpoints

### Get Fiat Currency Rates

**Endpoint:** `/api/rates/fiat`  
**Method:** `GET`  
**Description:** Retrieves the latest fiat currency rates against USD.

**Response Example:**

```json
{
  "AUD": 1.5890902106,
  "BGN": 1.865300307,
  "BRL": 5.9100506841,
  "CAD": 1.4384701495,
  "CHF": 0.9068501237,
  "CNY": 7.2575810116,
  "EUR": 0.9552900958,
  "GBP": 0.8027701124,
  "USD": 1.0,
  ...
}
```

### Get All Cryptocurrency Rates

**Endpoint:** `/api/rates/crypto`  
**Method:** `GET`  
**Description:** Retrieves a list of all cryptocurrencies with their current rates in USD.

**Response Example:**

```json
[
  {
    "id": "btc-bitcoin",
    "name": "Bitcoin",
    "symbol": "BTC",
    "rank": 1,
    "quotes": {
      "USD": {
        "price": 27000.0,
        ...
      }
    }
  },
  ...
]
```

### Get Cryptocurrency Rate by ID

**Endpoint:** `/api/rates/crypto/{id}`  
**Method:** `GET`  
**Description:** Retrieves details of a specific cryptocurrency by its CoinPaprika ID.

**Path Parameters:**

- **`id`**: The unique identifier of the cryptocurrency (e.g., `btc-bitcoin`).

**Response Example:**

```json
{
  "id": "btc-bitcoin",
  "name": "Bitcoin",
  "symbol": "BTC",
  "rank": 1,
  "quotes": {
    "USD": {
      "price": 27000.0,
      ...
    }
  }
}
```

### Convert Between Currencies

**Endpoint:** `/api/convert`  
**Method:** `GET`  
**Description:** Converts an amount from one currency to another, supporting both fiat and cryptocurrencies.

**Query Parameters:**

- **`from`**: The source currency code (e.g., `BTC`, `USD`).
- **`to`**: The target currency code (e.g., `EUR`, `AUD`).
- **`amount`**: The amount to convert.

**Response Example:**

```json
{
  "from": "BTC",
  "to": "AUD",
  "originalAmount": 1.0,
  "convertedAmount": 24776.0
}
```

## Usage Examples

### Convert 30 BTC to AUD

**Request:**

```bash
curl -X GET "http://localhost:9191/api/convert?from=btc&to=usd&amount=30"
```

**Response:**

```json
{
  "from": "btc",
  "to": "usd",
  "originalAmount": 30.0,
  "convertedAmount": 3055437.0
}
```

### Fetch Fiat Currency Rates

**Request:**

```bash
curl -X GET "http://localhost:9191/api/rates/fiat"
```

**Response:**

```json
{
  "AUD": 1.5890902106,
  "BGN": 1.865300307,
  "BRL": 5.9100506841,
  "CAD": 1.4384701495,
  "CHF": 0.9068501237,
  "CNY": 7.2575810116,
  "EUR": 0.9552900958,
  "GBP": 0.8027701124,
  "USD": 1.0,
  ...
}
```

### Fetch Cryptocurrency Rates

**Request:**

```bash
curl -X GET "http://localhost:9191/api/rates/crypto"
```

**Response:**

```json
[
  {
    "id": "btc-bitcoin",
    "name": "Bitcoin",
    "symbol": "BTC",
    "rank": 1,
    "quotes": {
      "USD": {
        "price": 103000.0,
        ...
      }
    }
  },
  {
    "id": "eth-ethereum",
    "name": "Ethereum",
    "symbol": "ETH",
    "rank": 2,
    "quotes": {
      "USD": {
        "price": 1800.0,
        ...
      }
    }
  },
  ...
]
```

### Fetch Cryptocurrency Rate by ID

**Request:**

```bash
curl -X GET "http://localhost:9191/api/rates/crypto/eth-ethereum"
```

**Response:**

```json
{
  "id": "eth-ethereum",
  "name": "Ethereum",
  "symbol": "ETH",
  "rank": 2,
  "quotes": {
    "USD": {
      "price": 1800.0,
      ...
    }
  }
}
```

## Error Handling

The application includes basic error handling for scenarios such as:

- **Invalid Currency Codes**: Returns a `400 Bad Request` with an error message if the provided currency codes are not supported.
- **Duplicate Symbols**: Handles cases where multiple cryptocurrencies share the same symbol by using unique identifiers.
- **External API Failures**: Returns appropriate error messages if external APIs like CoinPaprika or Free Currency API fail or return unexpected data.

### Example Error Response

**Request:**

```bash
curl -X GET "http://localhost:9191/api/convert?from=btc&to=unknown&amount=10"
```

**Response:**

```json
{
  "timestamp": "2025-01-27T17:48:40.172+03:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Unknown currency: UNKNOWN",
  "path": "/api/convert"
}
```