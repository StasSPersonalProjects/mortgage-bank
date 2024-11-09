/* eslint-disable react/prop-types */
import { createPortal } from 'react-dom';
import { forwardRef, useImperativeHandle, useRef } from 'react';
import classes from './CustomModal.module.css';

const CustomModal = forwardRef(function CustomModal({ message }, ref) {

  const dialog = useRef();

  useImperativeHandle(ref, () => {
    return {
      open: () => {
        dialog.current.showModal();
      }
    }
  });

  return createPortal(
    <dialog ref={dialog} className={classes['dialog-popup']} >
      <p>{message}</p>
      <form method='dialog'>
        <button>Close</button>
      </form>
    </dialog>,
    document.getElementById('modal')
  )
});

export default CustomModal;