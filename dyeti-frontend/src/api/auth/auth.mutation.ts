import { apiRequest } from '../axios';
import { HttpMethod, LoginRequest, LoginResponse, LogoutResponse, RegisterRequest, User } from '../types';

const logIn = async ({ username, password }: LoginRequest) => {
  const formData = new URLSearchParams();
  formData.append('username', username);
  formData.append('password', password);

  return apiRequest<LoginResponse>(HttpMethod.POST, '/auth/login', formData, {
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
  });
};

const logOut = async () => {
  return apiRequest<LogoutResponse>(HttpMethod.POST, '/auth/logout');
};

const register = async (data: RegisterRequest) => {
  return apiRequest<User>(HttpMethod.POST, '/auth/register', data);
};

const checkAuth = async () => {
  return apiRequest<User>(HttpMethod.GET, '/auth/check');
};

const AUTH_MUTATION = {
  LOGIN: logIn,
  LOGOUT: logOut,
  REGISTER: register,
  CHECK: checkAuth,
};

export default AUTH_MUTATION;
