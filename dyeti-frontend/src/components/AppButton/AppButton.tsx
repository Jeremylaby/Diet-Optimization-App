import React from 'react';
import { useTheme } from 'styled-components';
import { StyledButton } from './AppButton.styles.ts';

type Size = 'sm' | 'md' | 'lg';
type IconPosition = 'left' | 'right';

interface AppButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children?: React.ReactNode;
  fullWidth?: boolean;
  size?: Size;
  icon?: React.ReactNode;
  iconPosition?: IconPosition;
  reversed?: boolean;
  disabled?: boolean;
  animation?: boolean;
}

const AppButton: React.FC<AppButtonProps> = ({
  children,
  size = 'md',
  icon,
  iconPosition = 'left',
  reversed = false,
  disabled = false,
  animation = false,
  fullWidth = false,
  onClick,
  ...rest
}) => {
  const theme = useTheme();
  const justifyContent = icon ? 'space-between' : 'center';
  const backgroundColor = reversed ? theme.colors.primary[100] : theme.colors.primary[600];
  const textColor = reversed ? theme.colors.primary[600] : theme.colors.primary[100];
  const padding = theme.buttonPadding[size];
  const fontSize = theme.typography.fontSize[size];
  const boxShadow = theme.shadows.md;
  const borderRadius = theme.borderRadius.lg;

  const handleClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    if (disabled) return;
    if (onClick) onClick(e);
  };

  return (
    <StyledButton
      type="button"
      onClick={handleClick}
      disabled={disabled}
      $fullWidth={fullWidth}
      $disabled={disabled}
      $animation={animation}
      $background={backgroundColor}
      $color={textColor}
      $padding={padding}
      $fontSize={fontSize}
      $justify={justifyContent}
      $boxShadow={boxShadow}
      $borderRadius={borderRadius}
      {...rest}
    >
      {icon && iconPosition === 'left' && icon}
      {children}
      {icon && iconPosition === 'right' && icon}
    </StyledButton>
  );
};

export default AppButton;
