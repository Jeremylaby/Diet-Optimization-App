import { useState } from 'react';
import * as Ui from './Navbar.styles';
import dyetiLogo from '../../assets/dyeti-logo.svg';

type NavItem = {
  route: string;
  label: string;
};

const navItems: NavItem[] = [
  { route: '/', label: 'Home' },
  { route: '/plans', label: 'My Plans' },
  { route: '/products', label: 'Products' },
];

const Navbar = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  return (
    <>
      <Ui.Container>
        <Ui.HomepageLink to="/">
          <Ui.Logo src={dyetiLogo} alt="DYeti logo" />
          <Ui.Title>DYeti</Ui.Title>
        </Ui.HomepageLink>

        <Ui.MenuIconContainer onClick={() => setIsMenuOpen(prev => !prev)}>
          {isMenuOpen ? <Ui.MenuOpenIcon /> : <Ui.MenuIcon />}
        </Ui.MenuIconContainer>

        <Ui.DesktopNav>
          <Ui.DesktopNavList>
            {navItems.map(item => (
              <li key={item.route}>
                <Ui.DesktopNavLink to={item.route} end>
                  {item.label}
                </Ui.DesktopNavLink>
              </li>
            ))}
          </Ui.DesktopNavList>
        </Ui.DesktopNav>
      </Ui.Container>

      {isMenuOpen && (
        <Ui.MobileMenu>
          <nav>
            <Ui.MobileNavList>
              {navItems.map(item => (
                <li key={item.route}>
                  <Ui.MobileNavLink to={item.route} end>
                    {item.label}
                  </Ui.MobileNavLink>
                </li>
              ))}
            </Ui.MobileNavList>
          </nav>
        </Ui.MobileMenu>
      )}
    </>
  );
};

export default Navbar;
