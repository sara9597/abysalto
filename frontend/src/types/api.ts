export interface Product {
  id: number | null;
  title: string;
  description: string;
  price: number;
  discountPercentage: number;
  rating: number;
  stock: number;
  brand: string;
  category: string;
  thumbnail: string;
  images: string[];
}

export interface ProductResponse {
  products: Product[];
  total: number;
  skip: number;
  limit: number;
}

export interface User {
  userId: number;
  username: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  phone: string;
  address: string;
}

export interface UserRegistrationRequest {
  username: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  phone: string;
  address: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface CartItem {
  cartItemId: number;
  userId: number;
  productId: number;
  quantity: number;
  product?: Product;
}

export interface CartItemRequest {
  productId: number;
  quantity: number;
}

export interface Favorite {
  favoriteId: number;
  userId: number;
  productId: number;
  product?: Product;
}

export interface Category {
  slug: string;
  name: string;
  url: string;
}