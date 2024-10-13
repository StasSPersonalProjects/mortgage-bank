import { createBrowserRouter } from "react-router-dom";
import RootLayout from "../pages/RootLayout";
import HomePage from "../pages/homepage";
import OtpConfirmationPage from "../pages/OtpConfirmationPage";
import ContactsPage from "../pages/ContactsPage";

export const router = createBrowserRouter([
  {
    path: "/",
    id: "root",
    element: <RootLayout />,
    children: [
      {
        index: true,
        element: <HomePage />,
      },
      {
        path: "/confirmation-otp/new",
        element: <OtpConfirmationPage />,
      },
      {
        path: "/confirmation-otp/existing",
        element: <OtpConfirmationPage />,
      },
      {
        path: "/contacts",
        element: <ContactsPage />,
      },
    ],
  },
]);
