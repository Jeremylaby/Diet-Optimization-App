import { Outlet } from 'react-router-dom';
import Navbar from '../Navbar/Navbar';
import * as Ui from './AppLayout.styles';

const AppLayout = () => {
  return (
    <Ui.Container>
      <Navbar />
      <Ui.Content>
        <Outlet />
      </Ui.Content>
    </Ui.Container>
  );
};

export default AppLayout;
