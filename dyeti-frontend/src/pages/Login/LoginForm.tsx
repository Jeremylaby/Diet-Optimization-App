import { useState } from 'react';
import * as Ui from './Login.styles';
import Input from '../../components/Input/Input.tsx';
import { AppButton } from '../../components';
import { useAuth } from '../../components/providers/AuthProvider.tsx';
import { useNavigate } from 'react-router-dom';

const LoginForm = () => {
  const { login } = useAuth();
  const navigate = useNavigate();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [rememberMe, setRememberMe] = useState(false);
  const [errors, setErrors] = useState<{ username?: string; password?: string; global?: string }>({});

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors({});

    if (!username) return setErrors({ username: 'Username is required' });
    if (!password) return setErrors({ password: 'Password is required' });

    try {
      await login(username, password);
      navigate('/account');
    } catch (err) {
      if (err instanceof Error) {
        setErrors({ global: `Invalid username or password: ${err.message}` });
      } else {
        setErrors({ global: 'An unknown error occurred' });
      }
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <Input
        label="USERNAME"
        type="text"
        placeholder="yetiuser"
        value={username}
        onChange={e => setUsername(e.target.value)}
        error={errors.username}
      />

      <Input
        label="PASSWORD"
        type="password"
        placeholder="••••••••"
        value={password}
        onChange={e => setPassword(e.target.value)}
        error={errors.password}
      />

      <Ui.OptionsContainer>
        <Ui.RememberMe>
          <input type="checkbox" id="remember" checked={rememberMe} onChange={e => setRememberMe(e.target.checked)} />
          <span className="custom-checkbox" />
          <label htmlFor="remember">Remember me</label>
        </Ui.RememberMe>
        <Ui.ForgotPassword href="/forgot-password">Forgot password?</Ui.ForgotPassword>
      </Ui.OptionsContainer>

      <AppButton fullWidth animation type="submit">
        Login
      </AppButton>

      {errors.global && <Ui.Error>{errors.global}</Ui.Error>}
    </form>
  );
};

export default LoginForm;
