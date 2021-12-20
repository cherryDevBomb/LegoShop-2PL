const BASE_PATH = "http://localhost:8080/legoShopServices";
const CUSTOMERS_PATH = "/customers"


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