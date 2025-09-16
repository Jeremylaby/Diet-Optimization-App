import { styled } from 'styled-components';

export const InputWrapper = styled.div<{ hasError?: boolean }>`
  display: flex;
  flex-direction: column;
  margin-top: 16px;
`;

export const InputLabel = styled.h1`
  font-size: ${({ theme }) => theme.typography.fontSize.sm};

  margin: 2px;
  color: ${({ theme }) => theme.colors.neutrals[900]};
`;

export const InputField = styled.input<{ hasError?: boolean }>`
  padding: 12px;
  color: ${({ theme }) => theme.colors.neutrals[900]};
  background: ${({ theme }) => theme.colors.primary[100]};
  border: ${({ theme }) => theme.borderWidth.normal} solid
    ${({ hasError, theme }) => (hasError ? theme.colors.danger[500] : theme.colors.neutrals[600])};
  border-radius: ${({ theme }) => theme.borderRadius.md};
  font-size: ${({ theme }) => theme.typography.fontSize.md};
  caret-color: ${({ theme }) => theme.colors.neutrals[700]};
  &::placeholder {
    color: ${({ theme }) => theme.colors.neutrals[400]};
  }
  &:focus {
    outline: none;
    border-color: ${({ theme }) => theme.colors.primary[700]};
    box-shadow: 0 0 0 ${({ theme }) => theme.borderWidth.thin} ${({ theme }) => theme.colors.primary[700]};
  }

  &:hover {
    box-shadow: ${({ theme }) => theme.shadows.md};
  }
`;

export const InputError = styled.span`
  font-size: ${({ theme }) => theme.typography.fontSize.sm};
  color: ${({ theme }) => theme.colors.danger[500]};
  margin: 2px;
  font-style: italic;
`;
