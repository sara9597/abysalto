import React from 'react';
import { Row, Col, Button } from 'react-bootstrap';

interface CartSummaryProps {
  total: number;
  onClearCart: () => void;
  onCheckout: () => void;
}

const CartSummary: React.FC<CartSummaryProps> = ({
  total,
  onClearCart,
  onCheckout
}) => {
  return (
    <Row className="mt-4">
      <Col md={6}>
        <Button variant="warning" onClick={onClearCart}>
          Clear Cart
        </Button>
      </Col>
      <Col md={6} className="text-end">
        <h5>Total: ${total.toFixed(2)}</h5>
        <Button variant="success" size="lg" onClick={onCheckout}>
          Checkout
        </Button>
      </Col>
    </Row>
  );
};

export default CartSummary; 