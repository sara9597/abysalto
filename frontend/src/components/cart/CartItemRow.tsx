import React from 'react';
import { Button, Form } from 'react-bootstrap';
import { CartItem } from '../../types/api';

interface CartItemRowProps {
  item: CartItem;
  onUpdateQuantity: (productId: number, newQuantity: number) => void;
  onRemoveItem: (productId: number) => void;
}

const CartItemRow: React.FC<CartItemRowProps> = ({
  item,
  onUpdateQuantity,
  onRemoveItem
}) => {
  return (
    <tr key={item.cartItemId}>
      <td>
        <div className="d-flex align-items-center">
          <img
            src={item.product?.thumbnail}
            alt="Product"
            style={{ width: '50px', height: '50px', objectFit: 'cover' }}
            className="me-3"
          />
          <div>
            <h6>{item.product?.title || `Product ${item.productId}`}</h6>
            <small className="text-muted">{item.product?.category || 'Category'}</small>
          </div>
        </div>
      </td>
      <td className="fw-bold">
        ${item.product?.price || 0}
      </td>
      <td>
        <div className="d-flex align-items-center" style={{ width: '120px' }}>
          <Button
            variant="outline-secondary"
            size="sm"
            onClick={() => onUpdateQuantity(item.productId, item.quantity - 1)}
          >
            -
          </Button>
          <Form.Control
            type="number"
            value={item.quantity}
            min="1"
            readOnly
            className="text-center mx-2"
          />
          <Button
            variant="outline-secondary"
            size="sm"
            onClick={() => onUpdateQuantity(item.productId, item.quantity + 1)}
          >
            +
          </Button>
        </div>
      </td>
      <td className="fw-bold">
        ${((item.product?.price || 0) * item.quantity).toFixed(2)}
      </td>
      <td>
        <Button
          variant="danger"
          size="sm"
          onClick={() => onRemoveItem(item.productId)}
        >
          Remove
        </Button>
      </td>
    </tr>
  );
};

export default CartItemRow; 