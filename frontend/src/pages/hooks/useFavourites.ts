import {Favorite} from "../../types/api";
import {favoritesApi} from "../../services/api";
import { useNavigate } from 'react-router-dom';
import  { useState, useEffect } from 'react';

export const useFavourites = () => {
    const navigate = useNavigate();
    const [favorites, setFavorites] = useState<Favorite[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [currentUser] = useState(() => {
        const user = localStorage.getItem('currentUser');
        return user ? JSON.parse(user) : null;
    });

    useEffect(() => {
        if (currentUser) {
            loadFavorites();
        }
    }, [currentUser]);

    const loadFavorites = async () => {
        if (!currentUser) return;

        setLoading(true);
        setError(null);

        try {
            const items = await favoritesApi.getUserFavorites(currentUser.userId);
            setFavorites(items);
        } catch (err) {
            setError('Failed to load favorites');
            console.error('Error loading favorites:', err);
        } finally {
            setLoading(false);
        }
    };

    const handleRemoveFavorite = async (productId: number) => {
        if (!currentUser) return;

        try {
            await favoritesApi.removeFromFavorites(currentUser.userId, productId);
            loadFavorites();
        } catch (err) {
            alert('Failed to remove from favorites');
        }
    };

    const handleAddToCart = async (productId: number) => {
        if (!currentUser) return;

        try {
            await fetch(`http://localhost:8080/api/cart/add?userId=${currentUser.userId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ productId, quantity: 1 })
            });
            alert('Product added to cart!');
        } catch (err) {
            alert('Failed to add product to cart');
        }
    };
    return {
        currentUser,
        loading,
        error,
        favorites,
        handleRemoveFavorite,
        handleAddToCart,
        navigate
    } as const;
}