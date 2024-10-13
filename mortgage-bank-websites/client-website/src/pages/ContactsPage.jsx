import classes from "./styles/ContactsPage.module.css";

export default function ContactsPage() {
  return (
    <section className={classes.section}>
      <p>Contact details</p>
      <p>Phone number - 08-9390705</p>
      <p>Customer service - service@sandm.com</p>
      <div className={classes.logos}>
        <img src="/facebook.png" alt="facebook-logo" />
        <img src="/instagram.png" alt="instagram-logo" />
      </div>
    </section>
  );
}
