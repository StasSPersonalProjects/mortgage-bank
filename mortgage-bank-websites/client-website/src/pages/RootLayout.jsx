import { Outlet } from "react-router-dom";
import Header from "../components/Header";
import NavigationBar from "../components/NavigationBar";
import classes from "./styles/RootLayout.module.css";

export default function RootLayout() {
  return (
    <>
      <Header />
      <NavigationBar />
      <div className={classes["all-content"]}>
        <div className={classes["left-logo"]}>
          <img src="/6LNHch-LogoMakr.png" alt="bank-logo" />
        </div>
        <main className={classes["main-content"]}>
          <Outlet />
        </main>
        <div className={classes["right-logo"]}>
          <img src="/0y2fIE-LogoMakr.png" alt="person-logo" />
        </div>
      </div>
    </>
  );
}
