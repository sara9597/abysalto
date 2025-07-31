import {Product} from "../../types/api";
import {favoritesApi, productsApi} from "../../services/api";
import { useParams, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';


export const useProductDetails = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const [product, setProduct] = useState<Product | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isFavorite, setIsFavorite] = useState(false);
    const [currentUser] = useState(() => {
        const user = localStorage.getItem('currentUser');
        return user ? JSON.parse(user) : null;
    });

    useEffect(() => {
        if (id) {
            loadProduct();
        }
    }, [id]);

    useEffect(() => {
        if (product && currentUser) {
            checkFavoriteStatus();
        }
    }, [product, currentUser]);

    const loadProduct = async () => {
        if (!id) return;

        setLoading(true);
        setError(null);

        try {
            const productData = await productsApi.getById(parseInt(id));
            setProduct(productData);
        } catch (err) {
            setError('Failed to load product');
            console.error('Error loading product:', err);
        } finally {
            setLoading(false);
        }
    };

    const checkFavoriteStatus = async () => {
        if (!product || !currentUser) return;

        try {
            const status = await favoritesApi.checkFavorite(currentUser.userId, product.id!);
            setIsFavorite(status);
        } catch (err) {
            console.error('Error checking favorite status:', err);
        }
    };

    const handleAddToCart = async () => {
        if (!currentUser) {
            navigate('/login');
            return;
        }

        if (!product) return;

        try {
            await fetch(`http://localhost:8080/api/cart/add?userId=${currentUser.userId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ productId: product.id, quantity: 1 })
            });
            alert('Product added to cart!');
        } catch (err) {
            alert('Failed to add product to cart');
        }
    };

    const handleToggleFavorite = async () => {
        if (!currentUser) {
            navigate('/login');
            return;
        }

        if (!product) return;

        try {
            if (isFavorite) {
                await favoritesApi.removeFromFavorites(currentUser.userId, product.id!);
                setIsFavorite(false);
                alert('Product removed from favorites!');
            } else {
                await favoritesApi.addToFavorites(currentUser.userId, product.id!);
                setIsFavorite(true);
                alert('Product added to favorites!');
            }
        } catch (err) {
            alert('Failed to update favorites');
        }
    };
    return {
        loading,
        product,
        error,
        isFavorite,
        handleToggleFavorite,
        handleAddToCart
    } as const;
}