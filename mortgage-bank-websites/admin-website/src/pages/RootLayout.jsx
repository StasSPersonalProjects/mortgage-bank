import { Outlet } from "react-router-dom";
import { useSelector } from "react-redux";
import NavigationBar from "../components/NavigationBar";

export default function RootLayout() {
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const isAdminRole = useSelector((state) => state.auth.roles).includes(6875);

  return (
    <>
      {isAuthenticated && isAdminRole && <NavigationBar />}
      <main>
        <Outlet />
      </main>
    </>
  );
}
