import axios from 'axios';
import {
  Product,
  ProductResponse,
  User,
  UserRegistrationRequest,
  LoginRequest,
  CartItem,
  CartItemRequest,
  Favorite,
  Category
} from '../types/api';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const productsApi = {
  getAll: (skip = 0, limit = 10, sortBy = 'title', sortOrder = 'asc'): Promise<ProductResponse> =>
    api.get(`/products?skip=${skip}&limit=${limit}&sortBy=${sortBy}&sortOrder=${sortOrder}`).then(res => res.data),
  
  getById: (id: number): Promise<Product> =>
    api.get(`/products/${id}`).then(res => res.data),
  
  getCategories: (): Promise<Category[]> =>
    api.get('/products/categories').then(res => res.data),
  
  getByCategory: (category: string, skip = 0, limit = 10, sortBy = 'title', sortOrder = 'asc'): Promise<ProductResponse> =>
    api.get(`/products/category/${category}?skip=${skip}&limit=${limit}&sortBy=${sortBy}&sortOrder=${sortOrder}`).then(res => res.data),
  
  search: (query: string, skip = 0, limit = 10, sortBy = 'title', sortOrder = 'asc'): Promise<ProductResponse> =>
    api.get(`/products/search?query=${query}&skip=${skip}&limit=${limit}&sortBy=${sortBy}&sortOrder=${sortOrder}`).then(res => res.data),
};

export const usersApi = {
  register: (userData: UserRegistrationRequest): Promise<User> =>
    api.post('/users/register', userData).then(res => res.data),
  
  login: (loginData: LoginRequest): Promise<User> =>
    api.post('/users/login', loginData).then(res => res.data),
  
  getAll: (): Promise<User[]> =>
    api.get('/users').then(res => res.data),
  
  getById: (id: number): Promise<User> =>
    api.get(`/users/${id}`).then(res => res.data),
  
  getCurrent: (username: string): Promise<User> =>
    api.get(`/users/current?username=${username}`).then(res => res.data),
  
  update: (id: number, userData: UserRegistrationRequest): Promise<User> =>
    api.put(`/users/${id}`, userData).then(res => res.data),
  
  delete: (id: number): Promise<void> =>
    api.delete(`/users/${id}`),
};

export const cartApi = {
  getUserCart: (userId: number): Promise<CartItem[]> =>
    api.get(`/cart?userId=${userId}`).then(res => res.data),
  
  addToCart: (userId: number, cartItem: CartItemRequest): Promise<CartItem> =>
    api.post(`/cart/add?userId=${userId}`, cartItem).then(res => res.data),
  
  updateQuantity: (userId: number, productId: number, quantity: number): Promise<CartItem> =>
    api.put(`/cart/update?userId=${userId}&productId=${productId}&quantity=${quantity}`).then(res => res.data),
  
  removeFromCart: (userId: number, productId: number): Promise<void> =>
    api.delete(`/cart/remove?userId=${userId}&productId=${productId}`),
  
  clearCart: (userId: number): Promise<void> =>
    api.delete(`/cart/clear?userId=${userId}`),
};

export const favoritesApi = {
  getUserFavorites: (userId: number): Promise<Favorite[]> =>
    api.get(`/favorites?userId=${userId}`).then(res => res.data),
  
  addToFavorites: (userId: number, productId: number): Promise<Favorite> =>
    api.post(`/favorites/add?userId=${userId}&productId=${productId}`).then(res => res.data),
  
  removeFromFavorites: (userId: number, productId: number): Promise<void> =>
    api.delete(`/favorites/remove?userId=${userId}&productId=${productId}`),
  
  checkFavorite: (userId: number, productId: number): Promise<boolean> =>
    api.get(`/favorites/check?userId=${userId}&productId=${productId}`).then(res => res.data),
};

export default api; 