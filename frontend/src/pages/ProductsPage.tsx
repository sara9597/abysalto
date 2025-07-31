import React from 'react';
import { Alert } from 'react-bootstrap';
import { useProduct } from './hooks/useProduct';
import { 
  LoadingComponent, 
  PageHeader, 
  ProductFilters, 
  ProductGrid, 
  ProductPagination 
} from '../components';

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
  } = useProduct();

  if (loading) {
    return <LoadingComponent />;
  }

  return (
    <div>
      <PageHeader title="Products" />
      
      {error && <Alert variant="danger">{error}</Alert>}

      <ProductFilters
        searchQuery={searchQuery}
        onSearchChange={setSearchQuery}
        onSearchSubmit={handleSearch}
        selectedCategory={selectedCategory}
        onCategoryChange={(category) => {
          setSelectedCategory(category);
          setCurrentPage(1);
        }}
        categories={categories}
        sortBy={sortBy}
        onSortChange={handleSortChange}
        sortOrder={sortOrder}
        onSortOrderToggle={() => handleSortChange(sortBy)}
      />

      <ProductGrid
        products={products}
        onViewDetails={(productId) => navigate(`/product/${productId}`)}
        onAddToCart={handleAddToCart}
        onToggleFavorite={handleToggleFavorite}
      />

      <ProductPagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={setCurrentPage}
      />
    </div>
  );
};

export default ProductsPage; 