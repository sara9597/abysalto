import React from 'react';
import { Table } from 'react-bootstrap';
import { CartItem } from '../../types/api';
import CartItemRow from './CartItemRow';

interface CartTableProps {
  cartItems: CartItem[];
  onUpdateQuantity: (productId: number, newQuantity: number) => void;
  onRemoveItem: (productId: number) => void;
}

const CartTable: React.FC<CartTableProps> = ({
  cartItems,
  onUpdateQuantity,
  onRemoveItem
}) => {
  return (
    <Table responsive striped>
      <thead>
        <tr>
          <th>Product</th>
          <th>Price</th>
          <th>Quantity</th>
          <th>Total</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {cartItems.map(item => (
          <CartItemRow
            key={item.cartItemId}
            item={item}
            onUpdateQuantity={onUpdateQuantity}
            onRemoveItem={onRemoveItem}
          />
        ))}
      </tbody>
    </Table>
  );
};

export default CartTable; 