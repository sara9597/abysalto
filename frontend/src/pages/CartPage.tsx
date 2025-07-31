import React from 'react';
import { Button, Alert, Row, Col } from 'react-bootstrap';
import {useCart} from "./hooks/useCart";
import {LoadingComponent} from "../components/LoadingComponent";
import {LoginWarning} from "../components/LoginWarning";
import {CartTable} from "./tables/CartTable";

const CartPage: React.FC = () => {
  const {
    currentUser,
    loading,
    error,
    cartItems,
    handleRemoveItem,
    handleClearCart,
    calculateTotal
  } = useCart()

  if (!currentUser) {
    return (
      <LoginWarning />
    );
  }

  if (loading) {
    return (
      <LoadingComponent />
    );
  }

  return (
    <div>
      <h2 className="mb-4">Shopping Cart</h2>
      
      {error && <Alert variant="danger">{error}</Alert>}

      {cartItems.length === 0 ? (
        <Alert variant="info">
          <h4>Your cart is empty</h4>
          <p>Add some products to your cart to see them here.</p>
          <Button href="/products" variant="primary">
            Continue Shopping
          </Button>
        </Alert>
      ) : (
        <>
          <CartTable cartItems={cartItems} handleRemoveItem={handleRemoveItem} handleUpdateQuantity={handleRemoveItem}/>

          <Row className="mt-4">
            <Col md={6}>
              <Button variant="warning" onClick={handleClearCart}>
                Clear Cart
              </Button>
            </Col>
            <Col md={6} className="text-end">
              <h5>Total: ${calculateTotal().toFixed(2)}</h5>
              <Button variant="success" size="lg">
                Checkout
              </Button>
            </Col>
          </Row>
        </>
      )}
    </div>
  );
};

export default CartPage; 