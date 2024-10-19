import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { LOGOUT_URL } from "../utils/urls";

export const logoutUser = createAsyncThunk(
  "authentication/logoutUser",
  async (token, { rejectWithValue }) => {
    try {
      const response = await fetch(LOGOUT_URL, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Logout failed");
      }
    } catch (error) {
      return rejectWithValue(error.message);
    }
  }
);

const initialAuthlState = {
  isAuthenticated: false,
  userFullName: "",
  roles: [],
  token: "",
  isLoading: true,
};

const authSlice = createSlice({
  name: "authentication",
  initialState: initialAuthlState,
  reducers: {
    authenticate(state, action) {
      sessionStorage.setItem("access_token", action.payload.access_token);
      state.token = action.payload.access_token;
      sessionStorage.setItem("user_full_name", action.payload.full_name);
      state.userFullName = action.payload.full_name;
      sessionStorage.setItem("roles", action.payload.roles);
      state.roles = action.payload.roles;
      state.isAuthenticated = true;
    },
    clear() {
      sessionStorage.clear();
    },
    rehydrate(state, action) {
      state.token = action.payload.access_token;
      state.isAuthenticated = true;
      state.roles = action.payload.roles;
      state.userFullName = action.payload.full_name;
    },
    setLoading(state, action) {
      state.isLoading = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(logoutUser.fulfilled, (state) => {
        sessionStorage.removeItem("access_token");
        sessionStorage.removeItem("user_full_name");
        sessionStorage.removeItem("roles");
        state.userFullName = "";
        state.roles = [];
        state.isAuthenticated = false;
      })
      .addCase(logoutUser.rejected, (state, action) => {
        console.error("Logout failed:", action.payload);
      });
  },
});

export default authSlice.reducer;
export const authActions = authSlice.actions;
