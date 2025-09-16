import { styled } from 'styled-components';
import { NavLink } from 'react-router-dom';
import { MdMenu, MdMenuOpen } from 'react-icons/md';

const HEADER_HEIGHT = '64px';

export const Container = styled.header`
  height: ${HEADER_HEIGHT};
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  background-color: ${({ theme }) => theme.colors.background};
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 10;
`;

export const HomepageLink = styled(NavLink)`
  display: flex;
  align-items: center;
  text-decoration: none;
  color: inherit;
  height: 100%;
`;

export const Logo = styled.img`
  height: 70%;
  aspect-ratio: 1 / 1;
  object-fit: contain;
`;

export const Title = styled.div`
  font-size: 1.5rem;
  font-weight: bold;
  color: ${({ theme }) => theme.colors.primary[900]};
`;

export const MenuIconContainer = styled.button`
  background: none;
  border: none;
  cursor: pointer;
  height: 100%;
  padding: 0;

  &:focus {
    outline: none;
  }

  &:focus-visible {
    outline: revert;
  }

  @media (min-width: ${({ theme }) => theme.breakpoints.md}) {
    display: none;
  }

  & > svg {
    height: 50%;
    aspect-ratio: 1 / 1;
    color: ${({ theme }) => theme.colors.primary[900]};
  }
`;

export const MenuIcon = styled(MdMenu).attrs(({ theme }) => ({
  size: 32,
  fill: theme.colors.primary[900],
}))``;

export const MenuOpenIcon = styled(MdMenuOpen).attrs(({ theme }) => ({
  size: 32,
  fill: theme.colors.primary[900],
}))``;

const NavListBase = styled.ul`
  height: 100%;
  display: flex;
  list-style: none;
  margin: 0;
  padding: 0;
`;

const NavLinkBase = styled(NavLink)`
  display: flex;
  align-items: center;
  justify-content: center;
  text-decoration: none;
  color: ${({ theme }) => theme.colors.neutrals[600]};
  font-size: ${({ theme }) => theme.typography.fontSize.md};
  font-weight: ${({ theme }) => theme.typography.fontWeight.regular};
  transition: color 0.2s ease-in;

  &:hover {
    background-color: ${({ theme }) => theme.colors.neutrals[100]};
    color: ${({ theme }) => theme.colors.neutrals[600]};
  }

  &.active {
    color: ${({ theme }) => theme.colors.primary[600]};
  }

  &.active:hover {
    color: ${({ theme }) => theme.colors.primary[600]};
  }
`;

export const DesktopNav = styled.nav`
  display: none;

  @media (min-width: ${({ theme }) => theme.breakpoints.md}) {
    display: block;
    height: 100%;
  }
`;

export const DesktopNavList = styled(NavListBase)`
  flex-direction: row;
`;

export const DesktopNavLink = styled(NavLinkBase)`
  height: 100%;
  padding: 16px 16px 12px;
  border-bottom: 4px solid transparent;
  transition:
    color 0.2s ease-in,
    border-bottom 0.2s ease-in;

  &.active {
    border-bottom: 4px solid ${({ theme }) => theme.colors.primary[600]};
  }
`;

export const MobileMenu = styled.aside`
  position: fixed;
  top: ${HEADER_HEIGHT};
  left: 0;
  width: 250px;
  height: calc(100vh - ${HEADER_HEIGHT});
  background-color: ${({ theme }) => theme.colors.background};
  padding: 16px 0;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.1);
  animation: slideIn 0.3s ease forwards;
  z-index: 9;

  nav {
    height: 100%;
  }

  @keyframes slideIn {
    from {
      transform: translateX(-100%);
    }
    to {
      transform: translateX(0);
    }
  }

  @media (min-width: ${({ theme }) => theme.breakpoints.md}) {
    display: none;
  }
`;

export const MobileNavList = styled(NavListBase)`
  flex-direction: column;
`;

export const MobileNavLink = styled(NavLinkBase)`
  height: 100%;
  padding: 16px;
  border-right: 4px solid transparent;
  transition:
    color 0.2s ease-in,
    border-right 0.2s ease-in;

  &.active {
    border-right: 4px solid ${({ theme }) => theme.colors.primary[600]};
  }
`;
