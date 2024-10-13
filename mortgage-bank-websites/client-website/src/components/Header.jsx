import classes from "./styles/Header.module.css";

export default function Header() {
  return (
    <header className={classes.header}>
      <div>
        <img
          className={classes["website-logo"]}
          src="/3FtWc3-LogoMakr.png"
          alt="website-logo"
        />
      </div>
      <div className={classes['text-section']}>
        <p>Lowest interest rates and best service!</p>
      </div>
    </header>
  );
}
