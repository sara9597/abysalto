import { Alert } from 'react-bootstrap';

export const LoginWarning = () => {
  return (
      <Alert variant="warning">
          Please <a href="/login">login</a> to view your cart.
      </Alert>
  )
};