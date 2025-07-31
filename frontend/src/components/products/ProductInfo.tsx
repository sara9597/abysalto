import React from 'react';
import { Card, Button, Badge } from 'react-bootstrap';
import { Product } from '../../types/api';

interface ProductInfoProps {
  product: Product;
  isFavorite: boolean;
  onAddToCart: () => void;
  onToggleFavorite: () => void;
}

const ProductInfo: React.FC<ProductInfoProps> = ({
  product,
  isFavorite,
  onAddToCart,
  onToggleFavorite
}) => {
  return (
    <Card>
      <Card.Body>
        <h2>{product.title}</h2>
        <div className="mb-3">
          <Badge bg="primary" className="me-2">{product.category}</Badge>
          <Badge bg="secondary">{product.brand}</Badge>
        </div>
        
        <div className="mb-3">
          <span className="text-warning fs-4">
            {'★'.repeat(Math.floor(product.rating))}
            {product.rating % 1 > 0.5 ? '★' : ''}
          </span>
          <span className="ms-2">{product.rating} ({product.stock} in stock)</span>
        </div>
        
        <div className="mb-3">
          <span className="fs-3 fw-bold text-success">${product.price}</span>
          {product.discountPercentage > 0 && (
            <span className="text-muted ms-2">
              <del>${(product.price / (1 - product.discountPercentage / 100)).toFixed(2)}</del>
              <span className="text-danger ms-1">-{product.discountPercentage}%</span>
            </span>
          )}
        </div>
        
        <p className="text-muted">{product.description}</p>
        
        <div className="d-grid gap-2">
          <Button variant="success" size="lg" onClick={onAddToCart}>
            Add to Cart
          </Button>
          <Button
            variant={isFavorite ? "danger" : "outline-danger"}
            onClick={onToggleFavorite}
          >
            {isFavorite ? '♥ Remove from Favorites' : '♡ Add to Favorites'}
          </Button>
        </div>
      </Card.Body>
    </Card>
  );
};

export default ProductInfo; 