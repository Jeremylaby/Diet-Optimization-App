import { createContext, useContext } from 'react';
import { useCheckAuth, useLogIn, useLogOut, useRegister } from '../../api/auth/hooks';
import { User } from '../../api/types.ts';

type AuthContextType = {
  user: User | null;
  isLoading: boolean;
  login: (username: string, password: string) => Promise<void>;
  logout: () => Promise<void>;
  register: (data: { username: string; email: string; password: string }) => Promise<void>;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  // 1. sprawdzamy sesję i pobieramy UserDTO (lub null)
  const { data: user, isLoading } = useCheckAuth();

  // 2. mutacje (akcje)
  const loginMutation = useLogIn();
  const logoutMutation = useLogOut();
  const registerMutation = useRegister();

  // 3. wrappery na mutacje – po sukcesie check się odświeży
  const login = async (username: string, password: string) => {
    await loginMutation.mutateAsync({ username, password });
  };

  const logout = async () => {
    await logoutMutation.mutateAsync();
  };

  const register = async (data: { username: string; email: string; password: string }) => {
    await registerMutation.mutateAsync(data);
  };

  return (
    <AuthContext.Provider
      value={{
        user: user ?? null,
        isLoading,
        login,
        logout,
        register,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used inside AuthProvider');
  return ctx;
};
