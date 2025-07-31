import {CartItem} from "../../types/api";
import {cartApi} from "../../services/api";
import { useState, useEffect } from 'react';

export const useCart = () => {
    const [cartItems, setCartItems] = useState<CartItem[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [currentUser] = useState(() => {
        const user = localStorage.getItem('currentUser');
        return user ? JSON.parse(user) : null;
    });

    useEffect(() => {
        if (currentUser) {
            loadCart();
        }
    }, [currentUser]);

    const loadCart = async () => {
        if (!currentUser) return;

        setLoading(true);
        setError(null);

        try {
            const items = await cartApi.getUserCart(currentUser.userId);
            setCartItems(items);
        } catch (err) {
            setError('Failed to load cart');
            console.error('Error loading cart:', err);
        } finally {
            setLoading(false);
        }
    };

    const handleUpdateQuantity = async (productId: number, newQuantity: number) => {
        if (!currentUser) return;

        try {
            if (newQuantity <= 0) {
                await cartApi.removeFromCart(currentUser.userId, productId);
            } else {
                await cartApi.updateQuantity(currentUser.userId, productId, newQuantity);
            }
            loadCart();
        } catch (err) {
            alert('Failed to update quantity');
        }
    };

    const handleRemoveItem = async (productId: number) => {
        if (!currentUser) return;

        if (window.confirm('Are you sure you want to remove this item?')) {
            try {
                await cartApi.removeFromCart(currentUser.userId, productId);
                loadCart();
            } catch (err) {
                alert('Failed to remove item');
            }
        }
    };

    const handleClearCart = async () => {
        if (!currentUser) return;

        if (window.confirm('Are you sure you want to clear your entire cart?')) {
            try {
                await cartApi.clearCart(currentUser.userId);
                loadCart();
            } catch (err) {
                alert('Failed to clear cart');
            }
        }
    };

    const calculateTotal = () => {
        return cartItems.reduce((total, item) => {
            const price = item.product?.price || 0;
            return total + (price * item.quantity);
        }, 0);
    };

    return {currentUser, loading, error, cartItems, handleUpdateQuantity, handleRemoveItem, handleClearCart, calculateTotal} as const;
}