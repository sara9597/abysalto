import React from 'react';
import { Alert } from 'react-bootstrap';
import { useFavourites } from './hooks/useFavourites';
import { 
  LoginWarning, 
  LoadingComponent, 
  PageHeader, 
  ProductGrid, 
  EmptyState 
} from '../components';

const FavoritesPage: React.FC = () => {
  const {
    currentUser,
    loading,
    error,
    favorites,
    handleRemoveFavorite,
    handleAddToCart,
    navigate
  } = useFavourites();

  if (!currentUser) {
    return <LoginWarning />;
  }

  if (loading) {
    return <LoadingComponent />;
  }

  return (
    <div>
      <PageHeader title="My Favorites" />
      
      {error && <Alert variant="danger">{error}</Alert>}

      {favorites.length === 0 ? (
        <EmptyState
          title="No favorites yet"
          message="Add some products to your favorites to see them here."
          actionText="Browse Products"
          actionHref="/products"
        />
      ) : (
        <ProductGrid
          products={favorites.map(fav => fav.product!).filter(Boolean)}
          onViewDetails={(productId) => navigate(`/product/${productId}`)}
          onAddToCart={handleAddToCart}
          onRemoveFromFavorites={handleRemoveFavorite}
          showFavoriteButton={false}
          showRemoveButton={true}
        />
      )}
    </div>
  );
};

export default FavoritesPage; 