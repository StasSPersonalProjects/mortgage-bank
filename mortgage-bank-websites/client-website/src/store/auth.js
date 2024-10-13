import { createSlice } from "@reduxjs/toolkit";

const initialAuthState = { isAuthenticated: false };

const authSlice = createSlice({
  name: "authentication",
  initialState: initialAuthState,
  reducers: {
    login: (state, action) => {
      let token = action.payload;
      sessionStorage.setItem("token", token);
      state.isAuthenticated = true;
    },
    logout: (state) => {
      sessionStorage.removeItem("token");
      state.isAuthenticated = false;
    },
  },
});

export default authSlice.reducer;
export const authActions = authSlice.actions;
