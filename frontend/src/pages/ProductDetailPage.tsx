import React from 'react';
import { Row, Col, Alert } from 'react-bootstrap';
import { useProductDetails } from './hooks/useProductDetails';
import { LoadingComponent, ProductImageCarousel, ProductInfo } from '../components';

const ProductDetailPage: React.FC = () => {
  const {
    loading,
    product,
    error,
    isFavorite,
    handleToggleFavorite,
    handleAddToCart
  } = useProductDetails();

  if (loading) {
    return <LoadingComponent />;
  }

  if (error || !product) {
    return (
      <Alert variant="danger">
        {error || 'Product not found'}
      </Alert>
    );
  }

  return (
    <div>
      <Row>
        <Col md={6}>
          <ProductImageCarousel
            images={product.images}
            productTitle={product.title}
          />
        </Col>
        <Col md={6}>
          <ProductInfo
            product={product}
            isFavorite={isFavorite}
            onAddToCart={handleAddToCart}
            onToggleFavorite={handleToggleFavorite}
          />
        </Col>
      </Row>
    </div>
  );
};

export default ProductDetailPage; 