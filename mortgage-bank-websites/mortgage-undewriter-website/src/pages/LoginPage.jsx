import { useState } from "react";
import EmployeeLoginData from "../model/EmployeeLoginData";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async (event) => {
    event.preventDefault();
    const loginData = new EmployeeLoginData(username, password);
    console.log(loginData);

    // TODO - send http request
    // TODO - receive token and save it
    // TODO - redirect to working page
  };

  return (
    <form onSubmit={handleLogin}>
      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        required
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
      />
      <button>Login</button>
    </form>
  );
}
