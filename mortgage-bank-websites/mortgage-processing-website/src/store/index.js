import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./auth";
import workFieldReducer from "./workField";

const store = configureStore({
  reducer: {
    auth: authReducer,
    workField: workFieldReducer,
  },
});

export default store;
