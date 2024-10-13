/* eslint-disable react/prop-types */
import { useEffect } from "react";
import { useSelector } from "react-redux";
import { Outlet, useNavigate } from "react-router-dom";

export default function RequiredAuth({ allowedRoles }) {
  const auth = useSelector((state) => state.auth);
  const navigate = useNavigate();

  useEffect(() => {
    if (auth.isLoading) {
      return;
    }

    if (!auth.isAuthenticated) {
      navigate("/");
      return;
    }

    if (!auth.roles.find((role) => allowedRoles.includes(role))) {
      navigate("/unauthorized");
      return;
    }
  }, [auth, allowedRoles, navigate]);

  return <Outlet />;
}
