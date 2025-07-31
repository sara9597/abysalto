import React, {useState, useEffect, useCallback, useRef} from 'react';
import { Container, Row, Col, Card, Button, Badge, Form, Alert, Spinner, Pagination } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { productsApi, cartApi, favoritesApi } from '../services/api';
import { Product, Category } from '../types/api';
import {useProduct} from "./hooks/useProduct";
import {LoadingComponent} from "../components/LoadingComponent";

const ProductsPage: React.FC = () => {
  const {
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
  } = useProduct()

  if (loading) {
    return (
      <LoadingComponent />
    );
  }

  return (
    <div>
      <h2 className="mb-4">Products</h2>
      
      {error && <Alert variant="danger">{error}</Alert>}

      <Row className="mb-4">
        <Col md={4}>
          <Form onSubmit={handleSearch}>
            <Form.Group className="d-flex">
              <Form.Control
                type="text"
                placeholder="Search products..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
              <Button type="submit" variant="primary" className="ms-2">
                Search
              </Button>
            </Form.Group>
          </Form>
        </Col>
        <Col md={4}>
          <Form.Select
            value={selectedCategory}
            onChange={(e) => {
              setSelectedCategory(e.target.value);
              setCurrentPage(1);
            }}
          >
            <option value="">All Categories</option>
            {categories.map(category => (
              <option key={category.slug} value={category.name}>{category.name}</option>
            ))}
          </Form.Select>
        </Col>
        <Col md={4}>
          <div className="d-flex gap-2">
            <Form.Select
              value={sortBy}
              onChange={(e) => handleSortChange(e.target.value)}
            >
              <option value="title">Sort by Title</option>
              <option value="price">Sort by Price</option>
              <option value="rating">Sort by Rating</option>
              <option value="stock">Sort by Stock</option>
              <option value="brand">Sort by Brand</option>
              <option value="category">Sort by Category</option>
            </Form.Select>
            <Button
              variant="outline-secondary"
              onClick={() => handleSortChange(sortBy)}
            >
              {sortOrder === 'asc' ? '↑' : '↓'}
            </Button>
          </div>
        </Col>
      </Row>

      <Row>
        {products.map(product => (
          <Col key={product.id} lg={3} md={4} sm={6} className="mb-4">
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
                    onClick={() => navigate(`/product/${product.id}`)}
                  >
                    View Details
                  </Button>
                  <Button
                    variant="outline-success"
                    size="sm"
                    onClick={() => handleAddToCart(product.id!)}
                  >
                    Add to Cart
                  </Button>
                  <Button
                    variant="outline-danger"
                    size="sm"
                    onClick={() => handleToggleFavorite(product.id!)}
                  >
                    ♥
                  </Button>
                </div>
              </Card.Footer>
            </Card>
          </Col>
        ))}
      </Row>

      {/* Pagination */}
      {totalPages > 1 && (
        <div className="d-flex justify-content-center mt-4">
          <Pagination>
            <Pagination.First
              disabled={currentPage === 1}
              onClick={() => setCurrentPage(1)}
            />
            <Pagination.Prev
              disabled={currentPage === 1}
              onClick={() => setCurrentPage(currentPage - 1)}
            />
            
            {Array.from({ length: Math.min(5, totalPages) }, (_, i) => {
              const page = Math.max(1, Math.min(totalPages - 4, currentPage - 2)) + i;
              return (
                <Pagination.Item
                  key={page}
                  active={page === currentPage}
                  onClick={() => setCurrentPage(page)}
                >
                  {page}
                </Pagination.Item>
              );
            })}
            
            <Pagination.Next
              disabled={currentPage === totalPages}
              onClick={() => setCurrentPage(currentPage + 1)}
            />
            <Pagination.Last
              disabled={currentPage === totalPages}
              onClick={() => setCurrentPage(totalPages)}
            />
          </Pagination>
        </div>
      )}
    </div>
  );
};

export default ProductsPage; 