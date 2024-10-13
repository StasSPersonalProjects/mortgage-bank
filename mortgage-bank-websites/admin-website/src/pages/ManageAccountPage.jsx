import AccordionItem from "../components/AccordionItem";
import ChangePasswordForm from "../components/ChangePasswordForm";
import classes from "./styles/ManageAccountPage.module.css";

export default function ManageAccountPage() {
  return (
    <main className={classes.main}>
      <section>
        <AccordionItem title="Change My Password">
          <ChangePasswordForm />
        </AccordionItem>
      </section>
    </main>
  );
}
