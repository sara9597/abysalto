import React from 'react';
import { Row, Col } from 'react-bootstrap';
import { Product } from '../../types/api';
import ProductCard from './ProductCard';

interface ProductGridProps {
  products: Product[];
  onViewDetails: (productId: number) => void;
  onAddToCart: (productId: number) => void;
  onToggleFavorite?: (productId: number) => void;
  onRemoveFromFavorites?: (productId: number) => void;
  showFavoriteButton?: boolean;
  showRemoveButton?: boolean;
}

const ProductGrid: React.FC<ProductGridProps> = ({
  products,
  onViewDetails,
  onAddToCart,
  onToggleFavorite,
  onRemoveFromFavorites,
  showFavoriteButton = true,
  showRemoveButton = false
}) => {
  return (
    <Row>
      {products.map(product => (
        <Col key={product.id} lg={3} md={4} sm={6} className="mb-4">
          <ProductCard
            product={product}
            onViewDetails={onViewDetails}
            onAddToCart={onAddToCart}
            onToggleFavorite={onToggleFavorite}
            onRemoveFromFavorites={onRemoveFromFavorites}
            showFavoriteButton={showFavoriteButton}
            showRemoveButton={showRemoveButton}
          />
        </Col>
      ))}
    </Row>
  );
};

export default ProductGrid; 