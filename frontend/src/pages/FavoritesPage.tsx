import React from 'react';
import { Row, Col, Card, Button, Alert, Spinner } from 'react-bootstrap';
import {useFavourites} from "./hooks/useFavourites";
import {LoginWarning} from "../components/LoginWarning";
import {LoadingComponent} from "../components/LoadingComponent";

const FavoritesPage: React.FC = () => {

const {
  currentUser,
  loading,
  error,
  favorites,
  handleRemoveFavorite,
  handleAddToCart,
  navigate
} = useFavourites()

  if (!currentUser) {
    return (
      <LoginWarning />
    );
  }

  if (loading) {
    return (
      <LoadingComponent />
    );
  }

  return (
    <div>
      <h2 className="mb-4">My Favorites</h2>
      
      {error && <Alert variant="danger">{error}</Alert>}

      {favorites.length === 0 ? (
        <Alert variant="info">
          <h4>No favorites yet</h4>
          <p>Add some products to your favorites to see them here.</p>
          <Button href="/products" variant="primary">
            Browse Products
          </Button>
        </Alert>
      ) : (
        <Row>
          {favorites.map(favorite => (
            <Col key={favorite.favoriteId} lg={3} md={4} sm={6} className="mb-4">
              <Card className="h-100">
                <Card.Img
                  variant="top"
                  src={favorite.product?.thumbnail}
                  style={{ height: '200px', objectFit: 'cover' }}
                />
                <Card.Body className="d-flex flex-column">
                  <Card.Title className="h6">{favorite.product?.title}</Card.Title>
                  <Card.Text className="text-muted small flex-grow-1">
                    {favorite.product?.description?.substring(0, 100)}...
                  </Card.Text>
                  <div className="d-flex justify-content-between align-items-center mb-2">
                    <span className="fw-bold text-success">${favorite.product?.price}</span>
                  </div>
                </Card.Body>
                <Card.Footer>
                  <div className="d-flex justify-content-between">
                    <Button
                      variant="outline-primary"
                      size="sm"
                      onClick={() => navigate(`/product/${favorite.productId}`)}
                    >
                      View Details
                    </Button>
                    <Button
                      variant="outline-success"
                      size="sm"
                      onClick={() => handleAddToCart(favorite.productId)}
                    >
                      Add to Cart
                    </Button>
                    <Button
                      variant="outline-danger"
                      size="sm"
                      onClick={() => handleRemoveFavorite(favorite.productId)}
                    >
                      Remove
                    </Button>
                  </div>
                </Card.Footer>
              </Card>
            </Col>
          ))}
        </Row>
      )}
    </div>
  );
};

export default FavoritesPage; 