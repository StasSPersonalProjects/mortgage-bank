import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { RouterProvider } from "react-router-dom";
import { router } from "./utils/router";
import { authActions } from "./store/auth";

function App() {
  const dispatch = useDispatch();

  useEffect(() => {
    const token = sessionStorage.getItem("access_token");
    const roles = sessionStorage.getItem("roles");
    const fullName = sessionStorage.getItem("user_full_name");

    if (token) {
      dispatch(
        authActions.rehydrate({
          access_token: token,
          roles: roles,
          userFullName: fullName,
        })
      );
    } else {
      dispatch(authActions.setLoading(false));
    }
  }, [dispatch]);

  return <RouterProvider router={router} />;
}

export default App;
