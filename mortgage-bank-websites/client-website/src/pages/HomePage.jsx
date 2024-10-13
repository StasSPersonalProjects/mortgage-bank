import { NavLink } from "react-router-dom";
import classes from "./styles/HomePage.module.css";

export default function HomePage() {
  return (
    <div className={classes["main-sections"]}>
      <section className={classes.section}>
        <p className={classes["welcome-text"]}>
          Welcome to S&M Mortgage, where we turn homeownership dreams into
          reality. Our expert team offers personalized mortgage solutions with
          competitive rates and flexible terms. Whether you are buying your
          first home or refinancing, we make the process simple and stress-free.
          Let us help you secure the home you have always wanted.
        </p>
      </section>
      <section className={classes.section}>
        <p>How can we assist you?</p>
        <div className={classes["btns"]}>
          <button>
            <NavLink to="/confirmation-otp/new">
              Place new mortgage request
            </NavLink>
          </button>
          <button>
            <NavLink to="/confirmation-otp/existing">
              Check existing request status
            </NavLink>
          </button>
        </div>
      </section>
    </div>
  );
}
