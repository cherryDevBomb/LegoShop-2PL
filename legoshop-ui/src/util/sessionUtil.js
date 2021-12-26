
export const setUserOnSession = (user) => {
  const loggedUser = JSON.stringify(user);
  sessionStorage.setItem('user', loggedUser);
};

export const getLoggedUser = () => {
  return sessionStorage.user ? JSON.parse(sessionStorage.user) : null;
};

export const getLoggedUserId = () => {
  const parsedUser = getLoggedUser();
  return parsedUser ? parsedUser.customerId : null;
};

export const logoutUser = () => {
  sessionStorage.setItem('user', null);
};