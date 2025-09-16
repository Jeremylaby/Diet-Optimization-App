import { styled } from 'styled-components';

export const Container = styled.div`
  min-height: 100dvh;
  min-width: 100dvw;
  display: flex;
  flex-direction: column;
  position: relative;
`;

export const Content = styled.div`
  flex: 1 0 auto;
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.colors.neutrals[100]};
`;
