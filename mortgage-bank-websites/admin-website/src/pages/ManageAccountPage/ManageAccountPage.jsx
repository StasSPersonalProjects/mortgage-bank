import AccordionItem from "../../components/AccordionItem/AccordionItem";
import ChangePasswordForm from "../../components/ChangePasswordForm/ChangePasswordForm";
import classes from "./ManageAccountPage.module.css";

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
