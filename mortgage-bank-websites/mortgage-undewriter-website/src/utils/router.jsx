import { createBrowserRouter } from 'react-router-dom';
import LoginPage from '../pages/LoginPage';
import MainWorkPage from '../pages/MainWorkPage';

export const router = createBrowserRouter([
  {
    path: '/',
    id: 'root',
    element: <LoginPage />
  }, 
  {
    path: '/main/underwriter',
    element: <MainWorkPage />
  }
]);