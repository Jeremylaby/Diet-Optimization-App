import { styled } from 'styled-components';

export const PageTitle = styled.h1`
  font-size: ${({ theme }) => theme.typography.fontSize.xxl};
  font-weight: ${({ theme }) => theme.typography.fontWeight.bold};
  margin-bottom: 8px;
  color: ${({ theme }) => theme.colors.neutrals[900]};
`;
