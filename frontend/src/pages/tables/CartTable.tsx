import {CartItem} from "../../types/api";
import { Table, Button, Form } from 'react-bootstrap';

interface CartTableProps {
    cartItems: CartItem[];
    handleUpdateQuantity: (productId: number, quantity: number) => void;
    handleRemoveItem: (productId: number) => void;
}
export const CartTable = ({ cartItems, handleUpdateQuantity, handleRemoveItem} : CartTableProps) => {
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
                                onClick={() => handleUpdateQuantity(item.productId, item.quantity - 1)}
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
                                onClick={() => handleUpdateQuantity(item.productId, item.quantity + 1)}
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
                            onClick={() => handleRemoveItem(item.productId)}
                        >
                            Remove
                        </Button>
                    </td>
                </tr>
            ))}
            </tbody>
        </Table>
    )
}