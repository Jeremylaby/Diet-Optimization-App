import { styled, keyframes, css } from 'styled-components';

const pulseLoop = keyframes`
    0% {
        transform: scale(1);
    }
    50% {
        transform: scale(1.08);
    }
    80% {
        transform: scale(0.96);
    }
    100% {
        transform: scale(1);
    }
`;

export const StyledButton = styled.button<{
  $fullWidth?: boolean;
  $disabled?: boolean;
  $animation?: boolean;
  $background: string;
  $color: string;
  $padding: string;
  $fontSize: string;
  $justify: string;
  $boxShadow: string;
  $borderRadius: string;
}>`
  cursor: ${({ $disabled }) => ($disabled ? 'not-allowed' : 'pointer')};

  display: flex;
  flex-direction: row;
  gap: 10px;
  align-items: center;
  justify-content: ${({ $justify }) => $justify};

  margin: auto;
  padding: ${({ $padding }) => $padding};
  border-radius: ${({ $borderRadius }) => $borderRadius};

  font-size: ${({ $fontSize }) => $fontSize};
  line-height: 1;
  color: ${({ $color }) => $color};

  opacity: ${({ $disabled }) => ($disabled ? 0.6 : 1)};
  background: ${({ $background }) => $background};
  box-shadow: ${({ $boxShadow }) => $boxShadow};

  ${({ $fullWidth }) => $fullWidth && 'width: 100%;'}

  &:hover {
    border-color: ${({ $color }) => $color};
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.6);

    ${({ $animation }) =>
      $animation &&
      css`
        animation: ${pulseLoop} 1.5s linear infinite both;
      `}
  }
`;
