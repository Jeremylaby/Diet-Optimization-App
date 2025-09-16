import * as Ui from './Login.styles';
import dyeti from '../../assets/dyeti.svg';
import { PageTitle } from '../../components/Typography/PageTitle.tsx';
import { PageDescription } from '../../components/Typography/PageDescription.tsx';
import LoginForm from './LoginForm.tsx';

const Login = () => {
  return (
    <Ui.Container>
      <Ui.Card>
        <Ui.LogoContainer>
          <Ui.Logo src={dyeti} alt="DYeti logo" />
        </Ui.LogoContainer>

        <PageTitle>Welcome back!</PageTitle>
        <PageDescription>Log in to view your diet plans, manage preferences, and more.</PageDescription>

        <LoginForm />

        <Ui.Footer>
          Don’t have an account? <a href="/signup">Sign up</a>
        </Ui.Footer>
      </Ui.Card>
    </Ui.Container>
  );
};

export default Login;
