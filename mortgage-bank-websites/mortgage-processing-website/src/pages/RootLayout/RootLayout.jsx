import { Outlet } from "react-router-dom";
import { useSelector } from "react-redux";
import NavigationBar from "../../components/NavigationBar/NavigationBar";

export default function RootLayout() {
  const allowedRoles = [1349, 2458];
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const roles = useSelector((state) => state.auth.roles);

  const isAllowedRole = roles.some((role) => allowedRoles.includes(Number(role)));

  return (
    <>
      {isAuthenticated && isAllowedRole && <NavigationBar />}
      <main>
        <Outlet />
      </main>
    </>
  );
}

