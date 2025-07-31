import {Category, Product} from "../../types/api";
import {cartApi, favoritesApi, productsApi} from "../../services/api";
import {useState, useEffect, useCallback, useRef} from 'react';
import { useNavigate } from 'react-router-dom';

export const useProduct = () => {

    const [products, setProducts] = useState<Product[]>([]);
    const [categories, setCategories] = useState<Category[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [searchQuery, setSearchQuery] = useState('');
    const [selectedCategory, setSelectedCategory] = useState('');
    const [sortBy, setSortBy] = useState('title');
    const [sortOrder, setSortOrder] = useState('asc');
    const [currentUser, setCurrentUser] = useState<any>(null);
    const searchTimeout = useRef<NodeJS.Timeout | null>(null);


    const navigate = useNavigate();

    useEffect(() => {
        const user = localStorage.getItem('currentUser');
        if (user) {
            setCurrentUser(JSON.parse(user));
        }
    }, []);

    const loadCategories = async () => {
        try {
            const categoriesData = await productsApi.getCategories();
            setCategories(categoriesData);
        } catch (err) {
            console.error('Error loading categories:', err);
        }
    };

    const loadProducts = useCallback(async () => {
        try {
            setLoading(true);
            setError(null);

            const skip = (currentPage - 1) * 12;
            let response;

            if (searchQuery) {
                response = await productsApi.search(searchQuery, skip, 12, sortBy, sortOrder);
            } else if (selectedCategory) {
                response = await productsApi.getByCategory(selectedCategory, skip, 12, sortBy, sortOrder);
            } else {
                response = await productsApi.getAll(skip, 12, sortBy, sortOrder);
            }

            setProducts(response.products);
            setTotalPages(Math.ceil(response.total / 12));
        } catch (err) {
            setError('Failed to load products');
            console.error('Error loading products:', err);
        } finally {
            setLoading(false);
        }
    }, [currentPage, sortBy, sortOrder, searchQuery, selectedCategory]);

    useEffect(() => {
        if (searchTimeout.current) {
            clearTimeout(searchTimeout.current);
        }

        searchTimeout.current = setTimeout(() => {
            setCurrentPage(1);
            loadProducts();
        }, 500);

        return () => {
            if (searchTimeout.current) {
                clearTimeout(searchTimeout.current);
            }
        };
    }, [searchQuery, loadProducts]);


    useEffect(() => {
        loadCategories();
        loadProducts();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const handleAddToCart = async (productId: number) => {
        if (!currentUser || !currentUser.userId) {
            alert('Please log in to add items to cart');
            return;
        }

        try {
            await cartApi.addToCart(currentUser.userId, { productId, quantity: 1 });
            alert('Added to cart!');
        } catch (err) {
            console.error('Error adding to cart:', err);
            alert('Failed to add to cart');
        }
    };

    const handleToggleFavorite = async (productId: number) => {
        if (!currentUser || !currentUser.userId) {
            alert('Please log in to manage favorites');
            return;
        }

        try {
            const isFav = await favoritesApi.checkFavorite(currentUser.userId, productId);
            if (isFav) {
                await favoritesApi.removeFromFavorites(currentUser.userId, productId);
                alert('Removed from favorites!');
            } else {
                await favoritesApi.addToFavorites(currentUser.userId, productId);
                alert('Added to favorites!');
            }
        } catch (err) {
            console.error('Error toggling favorite:', err);
            alert('Failed to update favorites');
        }
    };

    const handleSearch = (e: React.FormEvent) => {
        e.preventDefault();
    };

    const handleSortChange = (newSortBy: string) => {
        if (sortBy === newSortBy) {
            setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
        } else {
            setSortBy(newSortBy);
            setSortOrder('asc');
        }
        setCurrentPage(1);
    };

    return {
        loading,
        currentUser,
        error,
        handleSearch,
        searchQuery,
        setSearchQuery,
        selectedCategory,
        setSelectedCategory,
        setCurrentPage,
        categories,
        sortBy,
        handleSortChange,
        sortOrder,
        products,
        navigate,
        handleAddToCart,
        handleToggleFavorite,
        totalPages,
        currentPage
    } as const;
}