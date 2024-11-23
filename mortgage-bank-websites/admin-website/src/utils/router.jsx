import { createBrowserRouter } from "react-router-dom";
import LoginPage from "../pages/LoginPage/LoginPage";
import RootLayout from "../pages/RootLayout";
import RequiredAuth from "../pages/RequiredAuth";
import HomePage from "../pages/HomePage/HomePage";
import Unauthorized from "../pages/Unauthorized/Unauthorized";
import ErrorPage from "../pages/ErrorPage/ErrorPage";
import ManageUsersPage from "../pages/ManageUsersPage/ManageUsersPage";
import ManageMarginsPage from "../pages/ManageMarginsPage/ManageMarginsPage";
import ManageAccountPage from "../pages/ManageAccountPage/ManageAccountPage";
import ManageInterestRatesPage from "../pages/ManageInterestRatesPage/ManageInterestRatesPage";

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
        element: <RequiredAuth allowedRoles={[6875]} />,
        children: [
          {
            path: "/home",
            id: "home",
            element: <HomePage />,
          },
          {
            path: "/users",
            id: "users",
            element: <ManageUsersPage />,
          },
          {
            path: "/margins",
            id: "margins",
            element: <ManageMarginsPage />,
          },
          {
            path: "/account",
            id: "account",
            element: <ManageAccountPage />,
          },
          {
            path: "/interest_rates",
            id: "interest_rates",
            element: <ManageInterestRatesPage />,
          },
        ],
      },
    ],
  },
]);
