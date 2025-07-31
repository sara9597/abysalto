import React from 'react';
import { Row, Col, Form, Button } from 'react-bootstrap';
import { Category } from '../../types/api';

interface ProductFiltersProps {
  searchQuery: string;
  onSearchChange: (query: string) => void;
  onSearchSubmit: (e: React.FormEvent) => void;
  selectedCategory: string;
  onCategoryChange: (category: string) => void;
  categories: Category[];
  sortBy: string;
  onSortChange: (sortBy: string) => void;
  sortOrder: string;
  onSortOrderToggle: () => void;
}

const ProductFilters: React.FC<ProductFiltersProps> = ({
  searchQuery,
  onSearchChange,
  onSearchSubmit,
  selectedCategory,
  onCategoryChange,
  categories,
  sortBy,
  onSortChange,
  sortOrder,
  onSortOrderToggle
}) => {
  return (
    <Row className="mb-4">
      <Col md={4}>
        <Form onSubmit={onSearchSubmit}>
          <Form.Group className="d-flex">
            <Form.Control
              type="text"
              placeholder="Search products..."
              value={searchQuery}
              onChange={(e) => onSearchChange(e.target.value)}
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
          onChange={(e) => onCategoryChange(e.target.value)}
        >
          <option value="">All Categories</option>
          {categories.map(category => (
            <option key={category.slug} value={category.name}>
              {category.name}
            </option>
          ))}
        </Form.Select>
      </Col>
      <Col md={4}>
        <div className="d-flex gap-2">
          <Form.Select
            value={sortBy}
            onChange={(e) => onSortChange(e.target.value)}
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
            onClick={onSortOrderToggle}
          >
            {sortOrder === 'asc' ? '↑' : '↓'}
          </Button>
        </div>
      </Col>
    </Row>
  );
};

export default ProductFilters; 