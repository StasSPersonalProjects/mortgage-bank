// authentication service URLs
export const AUTHENTICATION_URL =
  "http://localhost:8083/auth/employees/authenticate";

export const LOGOUT_URL = "http://localhost:8083/auth/employees/logout";

export const CHANGE_PASSWORD_URL =
  "http://localhost:8083/auth/employees/change_password";

export const FETCH_CONSULTANTS_URL =
  "http://localhost:8083/auth/common/find/all";

// margins and rates service URLs

export const GET_AVAILABLE_LOAN_TYPES_URL =
  "http://localhost:8085/margins/config/types";

//mortgage requests storage

export const FETCH_REQUESTS_URL =
  "http://localhost:8084/api/v1/storage/requests/pending";

export const FIND_REQUESTS_URL =
  "http://localhost:8084/api/v1/storage/requests/request/by";

export const UPDATE_MORTGAGE_REQUEST_URL =
  "http://localhost:8084/api/v1/storage/requests/update_request";

export const ADD_MORTGAGE_REQUEST_FIELD_URL =
  "http://localhost:8084/api/v1/storage/requests/add_field";
