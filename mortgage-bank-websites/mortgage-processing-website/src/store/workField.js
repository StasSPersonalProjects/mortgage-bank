import { createSlice } from "@reduxjs/toolkit";
import { _ } from "lodash";

const initialWorkFieldState = {
  isWorkFieldActive: false,
  workFieldTabs: [],
  active: "",
};

const workFieldSlice = createSlice({
  name: "workField",
  initialState: initialWorkFieldState,
  reducers: {
    enableWorkField(state) {
      state.isWorkFieldActive = true;
    },
    addWorkFieldTab(state, action) {
      const exists = state.workFieldTabs.some(
        (tab) => tab.id === action.payload.id
      );
      if (!exists) {
        state.workFieldTabs = [...state.workFieldTabs, action.payload];
      }
    },
    removeWorkFieldTab(state, action) {
      const index = state.workFieldTabs.findIndex(
        (tab) => tab.id === action.payload
      );
      state.workFieldTabs = state.workFieldTabs.filter(
        (tab) => tab.id !== action.payload
      );
      if (state.workFieldTabs.length > 0) {
        const newActiveIndex = index > 0 ? index - 1 : 0;
        state.active = state.workFieldTabs[newActiveIndex].label;
      } else {
        state.isWorkFieldActive = false;
        state.active = "";
      }
    },
    setActive(state, action) {
      state.active = action.payload;
    },
    updateMortgageRequestOwner(state, action) {
      const activeTabIndex = state.workFieldTabs.findIndex(
        (tab) => tab.label === state.active
      );
      if (activeTabIndex === -1) return;
      state.workFieldTabs[activeTabIndex].data.owner = action.payload;
    },
    updateMortgageRequestCustomerData(state, action) {
      const { path, value } = action.payload;
      const activeTabIndex = state.workFieldTabs.findIndex(
        (tab) => tab.label === state.active
      );
      if (activeTabIndex === -1) return;
      _.set(state.workFieldTabs[activeTabIndex].data, path, value);
    },
  },
});

export default workFieldSlice.reducer;
export const workFieldActions = workFieldSlice.actions;
