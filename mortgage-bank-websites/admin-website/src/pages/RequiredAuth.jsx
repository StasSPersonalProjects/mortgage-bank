/* eslint-disable react/prop-types */
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Outlet, useNavigate } from "react-router-dom";
import { authActions } from "../store/auth";

export default function RequiredAuth({ allowedRoles }) {
  const auth = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    if (auth.isLoading) {
      return;
    }

    if (!auth.roles.some((role) => allowedRoles.includes(role))) {
      dispatch(authActions.clear());
      navigate("/unauthorized", { replace: true });
      return;
    }

    if (!auth.isAuthenticated) {
      dispatch(authActions.clear());
      navigate("/", { replace: true });
      return;
    }
  }, [
    auth.isAuthenticated,
    auth.roles,
    auth.isLoading,
    allowedRoles,
    dispatch,
    navigate,
  ]);

  return <Outlet />;
}
