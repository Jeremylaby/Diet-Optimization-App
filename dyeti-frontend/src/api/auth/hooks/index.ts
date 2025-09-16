import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import AUTH_MUTATION from '../auth.mutation';
import AUTH_KEYS from '../auth.keys';

import { apiRequest } from '../../axios.ts';
import { HttpMethod, User } from '../../types.ts';

export const useLogIn = () => {
  return useMutation({
    mutationKey: AUTH_KEYS.LOGIN,
    mutationFn: AUTH_MUTATION.LOGIN,
    onSuccess: data => {
      // TODO: Handle successful login
      console.log('Login successful:', data.message);
    },
  });
};
export const useLogOut = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationKey: AUTH_KEYS.LOGOUT,
    mutationFn: AUTH_MUTATION.LOGOUT,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: AUTH_KEYS.CHECK });
    },
  });
};

export const useRegister = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationKey: AUTH_KEYS.REGISTER,
    mutationFn: AUTH_MUTATION.REGISTER,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: AUTH_KEYS.CHECK });
    },
  });
};

export const useCheckAuth = () => {
  return useQuery<User>({
    queryKey: AUTH_KEYS.CHECK,
    queryFn: () => apiRequest<User>(HttpMethod.GET, '/auth/check'),
    retry: false,
  });
};
