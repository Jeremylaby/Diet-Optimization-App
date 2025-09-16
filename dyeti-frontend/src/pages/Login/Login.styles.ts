import { styled } from 'styled-components';

export const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 64px);
  background-color: ${({ theme }) => theme.colors.neutrals[100]};
  padding: 16px;
  font-family: ${({ theme }) => theme.typography.fontFamily};
`;

export const Card = styled.div`
  width: 100%;
  max-width: 400px;
  background: ${({ theme }) => theme.colors.background};
  border-radius: 16px;
  box-shadow: ${({ theme }) => theme.shadows.lg};
  padding: 32px;
`;

export const Logo = styled.img`
  width: 50%;
  aspect-ratio: 1 / 1;
  object-fit: contain;
`;
export const LogoContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  color: inherit;
  width: 100%;
`;

export const ForgotPassword = styled.a`
  font-size: ${({ theme }) => theme.typography.fontSize.sm};
  color: ${({ theme }) => theme.colors.primary[700]};

  &:hover {
    text-decoration: underline;
  }
`;

export const RememberMe = styled.div`
  display: flex;
  align-items: center;
  font-size: ${({ theme }) => theme.typography.fontSize.sm};
  font-weight: ${({ theme }) => theme.typography.fontWeight.bold};
  color: ${({ theme }) => theme.colors.neutrals[900]};

  input {
    margin-right: 8px;
  }
`;

export const OptionsContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
`;

export const Error = styled.p`
  color: ${({ theme }) => theme.colors.danger[500]};
  font-size: ${({ theme }) => theme.typography.fontSize.sm};
`;
export const Footer = styled.div`
  text-align: center;
  margin-top: 8px;
  font-weight: ${({ theme }) => theme.typography.fontWeight.bold};
  font-size: ${({ theme }) => theme.typography.fontSize.sm};
  color: ${({ theme }) => theme.colors.neutrals[600]};

  a {
    color: ${({ theme }) => theme.colors.primary[700]};
    text-decoration: none;

    &:hover {
      text-decoration: underline;
    }
  }
`;
