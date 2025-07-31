import React from 'react';
import { Card, Button, Badge } from 'react-bootstrap';
import { Product } from '../../types/api';

interface ProductCardProps {
  product: Product;
  onViewDetails: (productId: number) => void;
  onAddToCart: (productId: number) => void;
  onToggleFavorite?: (productId: number) => void;
  onRemoveFromFavorites?: (productId: number) => void;
  showFavoriteButton?: boolean;
  showRemoveButton?: boolean;
}

const ProductCard: React.FC<ProductCardProps> = ({
  product,
  onViewDetails,
  onAddToCart,
  onToggleFavorite,
  onRemoveFromFavorites,
  showFavoriteButton = true,
  showRemoveButton = false
}) => {
  return (
    <Card className="h-100">
      <Card.Img
        variant="top"
        src={product.thumbnail}
        style={{ height: '200px', objectFit: 'cover' }}
      />
      <Card.Body className="d-flex flex-column">
        <Card.Title className="h6">{product.title}</Card.Title>
        <Card.Text className="text-muted small flex-grow-1">
          {product.description.substring(0, 100)}...
        </Card.Text>
        <div className="d-flex justify-content-between align-items-center mb-2">
          <Badge bg="primary">{product.category}</Badge>
          <span className="fw-bold text-success">${product.price}</span>
        </div>
        <div className="d-flex justify-content-between align-items-center">
          <small className="text-warning">
            {'★'.repeat(Math.floor(product.rating))}
            {product.rating % 1 > 0.5 ? '★' : ''}
            <span className="text-muted ms-1">{product.rating}</span>
          </small>
          <small className="text-muted">Stock: {product.stock}</small>
        </div>
      </Card.Body>
      <Card.Footer>
        <div className="d-flex justify-content-between">
          <Button
            variant="outline-primary"
            size="sm"
            onClick={() => onViewDetails(product.id!)}
          >
            View Details
          </Button>
          <Button
            variant="outline-success"
            size="sm"
            onClick={() => onAddToCart(product.id!)}
          >
            Add to Cart
          </Button>
          {showFavoriteButton && onToggleFavorite && (
            <Button
              variant="outline-danger"
              size="sm"
              onClick={() => onToggleFavorite(product.id!)}
            >
              ♥
            </Button>
          )}
          {showRemoveButton && onRemoveFromFavorites && (
            <Button
              variant="outline-danger"
              size="sm"
              onClick={() => onRemoveFromFavorites(product.id!)}
            >
              Remove
            </Button>
          )}
        </div>
      </Card.Footer>
    </Card>
  );
};

export default ProductCard; 