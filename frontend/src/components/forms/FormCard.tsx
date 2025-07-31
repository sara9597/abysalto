import React from 'react';
import { Card, Button, Alert } from 'react-bootstrap';

interface FormCardProps {
  title: string;
  children: React.ReactNode;
  onSubmit: (e: React.FormEvent) => void;
  loading?: boolean;
  error: string | null;
  submitText?: string;
  loadingText?: string;
  style?: React.CSSProperties;
}

const FormCard: React.FC<FormCardProps> = ({
  title,
  children,
  onSubmit,
  loading = false,
  error,
  submitText = 'Submit',
  loadingText = 'Loading...',
  style
}) => {
  return (
    <div className="d-flex justify-content-center">
      <Card style={style || { width: '400px' }}>
        <Card.Body>
          <Card.Title className="text-center mb-4">{title}</Card.Title>
          
          {error && <Alert variant="danger">{error}</Alert>}
          
          <form onSubmit={onSubmit}>
            {children}
            
            <div className="d-grid">
              <Button
                type="submit"
                variant="primary"
                disabled={loading}
              >
                {loading ? loadingText : submitText}
              </Button>
            </div>
          </form>
        </Card.Body>
      </Card>
    </div>
  );
};

export default FormCard; 