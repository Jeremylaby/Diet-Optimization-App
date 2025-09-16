import { BrowserRouter, Route, Routes } from 'react-router-dom';
import AppLayout from './components/AppLayout/AppLayout.tsx';
import { Home, Plans, Products, Themes, Login } from './pages/index.ts';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={'/'} element={<AppLayout />}>
          <Route index element={<Home />} />
          <Route path="/plans" element={<Plans />} />
          <Route path="/products" element={<Products />} />
          <Route path="/themes" element={<Themes />} />
          <Route path="/login" element={<Login />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
