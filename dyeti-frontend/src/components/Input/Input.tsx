import { InputWrapper, InputLabel, InputField, InputError } from './Input.styles';

type Props = {
  label: string;
  type?: string;
  placeholder?: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
};

const Input = ({ label, type = 'text', placeholder, value, onChange, error }: Props) => {
  return (
    <InputWrapper hasError={!!error}>
      <InputLabel>{label}</InputLabel>
      <InputField type={type} placeholder={placeholder} value={value} onChange={onChange} hasError={!!error} />
      {error && <InputError>{error}</InputError>}
    </InputWrapper>
  );
};

export default Input;
