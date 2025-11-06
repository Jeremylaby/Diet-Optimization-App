import { InputField, InputShell, ToggleBtn } from './Input.styles.ts';
import InputWrapper from '../InputWrapper.tsx';
import { useState } from 'react';
import { FaRegEye, FaRegEyeSlash } from 'react-icons/fa';
type Props = {
  label: string;
  type?: string;
  placeholder?: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
};

const Input = ({ label, type = 'text', placeholder, value, onChange, error }: Props) => {
  const [revealed, setRevealed] = useState(false);
  const isPassword = type === 'password';
  const effectiveType = isPassword && revealed ? 'text' : type;
  return (
    <InputWrapper label={label} error={error}>
      <InputShell>
        <InputField
          type={effectiveType}
          placeholder={placeholder}
          value={value}
          onChange={onChange}
          hasError={!!error}
        />
        {isPassword && (
          <ToggleBtn
            type="button"
            aria-label={revealed ? 'Hide password' : 'Show password'}
            title={revealed ? 'Hide password' : 'Show password'}
            onClick={() => setRevealed(v => !v)}
          >
            {revealed ? <FaRegEyeSlash /> : <FaRegEye />}
          </ToggleBtn>
        )}
      </InputShell>
    </InputWrapper>
  );
};

export default Input;
