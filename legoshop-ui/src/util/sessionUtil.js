
export const setUserOnSession = (user) => {
  const loggedUser = JSON.stringify(user);
  sessionStorage.setItem('user', loggedUser);
};

export const getLoggedUser = () => {
  return JSON.parse(sessionStorage.user);

};

export const getLoggedUserId = () => {
  const parsedUser = JSON.parse(sessionStorage.user);
  return parsedUser ? parsedUser.customerId : null;
};

export const logoutUser = () => {
  sessionStorage.setItem('user', null);
};