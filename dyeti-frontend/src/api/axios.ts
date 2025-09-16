// eslint-disable-next-line import/named
import axios, { AxiosError, AxiosRequestConfig } from 'axios';
import { HttpMethod } from './types';

const apiUrl = import.meta.env.VITE_API_URL;

const apiClient = axios.create({
  baseURL: apiUrl,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
  timeout: 10000,
});

apiClient.interceptors.response.use(
  response => response,
  (error: AxiosError) => {
    const errorMessage =
      error.response?.data && typeof error.response.data === 'object' && 'message' in error.response.data
        ? (error.response.data.message as string)
        : error.message || 'An unexpected error occurred';
    return Promise.reject(new Error(errorMessage));
  },
);

apiClient.interceptors.request.use(
  config => {
    // TODO: Access saved user data and add approptiate headers
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  },
);

export const apiRequest = async <T>(
  method: HttpMethod,
  url: string,
  data?: unknown,
  config?: AxiosRequestConfig,
): Promise<T> => {
  try {
    const response = await apiClient.request<T>({
      method,
      url,
      data,
      ...config,
    });

    return response.data;
  } catch (error) {
    throw error;
  }
};

export default apiClient;
