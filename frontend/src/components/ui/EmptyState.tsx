import React from 'react';
import { Alert, Button } from 'react-bootstrap';

interface EmptyStateProps {
  title: string;
  message: string;
  actionText?: string;
  actionHref?: string;
  actionVariant?: string;
}

const EmptyState: React.FC<EmptyStateProps> = ({
  title,
  message,
  actionText,
  actionHref,
  actionVariant = 'primary'
}) => {
  return (
    <Alert variant="info">
      <h4>{title}</h4>
      <p>{message}</p>
      {actionText && actionHref && (
        <Button href={actionHref} variant={actionVariant}>
          {actionText}
        </Button>
      )}
    </Alert>
  );
};

export default EmptyState; 