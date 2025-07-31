import React from 'react';
import { Carousel } from 'react-bootstrap';

interface ProductImageCarouselProps {
  images: string[];
  productTitle: string;
}

const ProductImageCarousel: React.FC<ProductImageCarouselProps> = ({
  images,
  productTitle
}) => {
  return (
    <Carousel>
      {images.map((image, index) => (
        <Carousel.Item key={index}>
          <img
            className="d-block w-100"
            src={image}
            alt={`${productTitle} - Image ${index + 1}`}
            style={{ height: '400px', objectFit: 'cover' }}
          />
        </Carousel.Item>
      ))}
    </Carousel>
  );
};

export default ProductImageCarousel; 