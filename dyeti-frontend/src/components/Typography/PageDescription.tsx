import { styled } from 'styled-components';

export const PageDescription = styled.p`
  font-size: ${({ theme }) => theme.typography.fontSize.md};
  color: ${({ theme }) => theme.colors.neutrals[600]};
  margin-bottom: 24px;
  font-weight: ${({ theme }) => theme.typography.fontWeight.bold};
`;
