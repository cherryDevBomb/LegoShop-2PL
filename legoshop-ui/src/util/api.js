const BASE_PATH = "http://localhost:8080/legoShopServices";
const CUSTOMERS_PATH = "/customers"
const PRODUCTS_PATH = "/legosets"
const ORDERS_PATH = "/orders"

export const login = (email, password) => {
  const url = new URL(BASE_PATH + CUSTOMERS_PATH + "/login");

  const requestBody = {
    "email": email,
    "password": password
  }

  return fetch(url, {
    method: "POST",
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestBody)
  }).then((response) => {
    if (response.status == 200) {
      return response.json();
    } else {
      return Promise.reject("Login failed");
    }
  });
};

export const signup = (firstName, lastName, email, password) => {
  const url = new URL(BASE_PATH + CUSTOMERS_PATH);

  const requestBody = {
    "firstName": firstName,
    "lastName": lastName,
    "email": email,
    "password": password,
  };

  return fetch(url, {
    method: "POST",
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestBody)
  }).then((response) => {
    if (response.status == 200) {
      return response.json();
    } else {
      return Promise.reject("Sign-up failed");
    }
  });
};

export const getAllProducts = () => {
  const url = new URL(BASE_PATH + PRODUCTS_PATH);

  return fetch(url, {
    method: "GET",
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
  }).then((response) => {
    if (response.status == 200) {
      return response.json();
    } else {
      return Promise.reject("Get products failed");
    }
  });
};

export const addOrder = (customerId, legosetId) => {
  const url = new URL(BASE_PATH + ORDERS_PATH);

  const requestBody = {
    "customerId": customerId,
    "legoSetId": legosetId
  }

  return fetch(url, {
    method: "POST",
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestBody)
  }).then((response) => {
    if (response.status == 200) {
      return response.json();
    } else {
      return Promise.reject("Add order failed");
    }
  });
};