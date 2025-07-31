import React from 'react';
import { Row, Col, Card, Button, Badge, Spinner, Alert, Carousel } from 'react-bootstrap';
import {useProductDetails} from "./hooks/useProductDetails";
import {LoadingComponent} from "../components/LoadingComponent";

const ProductDetailPage: React.FC = () => {

const {
  loading,
  product,
  error,
  isFavorite,
  handleToggleFavorite,
    handleAddToCart
} = useProductDetails()

  if (loading) {
    return (
      <LoadingComponent />
    );
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
          <Carousel>
            {product.images.map((image, index) => (
              <Carousel.Item key={index}>
                <img
                  className="d-block w-100"
                  src={image}
                  alt={`${product.title} - Image ${index + 1}`}
                  style={{ height: '400px', objectFit: 'cover' }}
                />
              </Carousel.Item>
            ))}
          </Carousel>
        </Col>
        <Col md={6}>
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
                <Button variant="success" size="lg" onClick={handleAddToCart}>
                  Add to Cart
                </Button>
                <Button
                  variant={isFavorite ? "danger" : "outline-danger"}
                  onClick={handleToggleFavorite}
                >
                  {isFavorite ? '♥ Remove from Favorites' : '♡ Add to Favorites'}
                </Button>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default ProductDetailPage; 