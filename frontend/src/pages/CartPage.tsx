import React from 'react';
import { Alert } from 'react-bootstrap';
import { useCart } from './hooks/useCart';
import { 
  LoginWarning, 
  LoadingComponent, 
  PageHeader, 
  CartTable, 
  CartSummary, 
  EmptyState 
} from '../components';

const CartPage: React.FC = () => {
  const {
    currentUser,
    loading,
    error,
    cartItems,
    handleRemoveItem,
    handleClearCart,
    calculateTotal
  } = useCart();

  if (!currentUser) {
    return <LoginWarning />;
  }

  if (loading) {
    return <LoadingComponent />;
  }

  return (
    <div>
      <PageHeader title="Shopping Cart" />
      
      {error && <Alert variant="danger">{error}</Alert>}

      {cartItems.length === 0 ? (
        <EmptyState
          title="Your cart is empty"
          message="Add some products to your cart to see them here."
          actionText="Continue Shopping"
          actionHref="/products"
        />
      ) : (
        <>
          <CartTable
            cartItems={cartItems}
            onUpdateQuantity={handleRemoveItem}
            onRemoveItem={handleRemoveItem}
          />

          <CartSummary
            total={calculateTotal()}
            onClearCart={handleClearCart}
            onCheckout={() => alert('Checkout functionality coming soon!')}
          />
        </>
      )}
    </div>
  );
};

export default CartPage; 