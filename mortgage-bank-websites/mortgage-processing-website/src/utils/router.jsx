import { createBrowserRouter } from "react-router-dom";
import LoginPage from "../pages/LoginPage/LoginPage";
import RootLayout from "../pages/RootLayout/RootLayout";
import Unauthorized from "../pages/Unauthorized/Unauthorized";
import RequiredAuth from "../pages/RequiredAuth/RequiredAuth";
import ErrorPage from "../pages/ErrorPage/ErrorPage";
import MainPage from "../pages/MainPage/MainPage";
import AccountPage from "../pages/AccountPage/AccountPage";

export const router = createBrowserRouter([
  {
    path: "/",
    id: "root",
    element: <RootLayout />,
    errorElement: <ErrorPage />,
    children: [
      {
        index: true,
        id: "login",
        path: "/",
        element: <LoginPage />,
      },
      {
        path: "/unauthorized",
        element: <Unauthorized />,
      },
      {
        element: <RequiredAuth allowedRoles={[1349, 2458]} />,
        children: [
          {
            path: "/main",
            element: <MainPage />
          },
          {
            path: "/account",
            element: <AccountPage />
          }
        ],
      },
    ],
  },
]);
