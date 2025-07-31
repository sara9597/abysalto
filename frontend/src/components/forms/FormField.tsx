import React from 'react';
import { Form } from 'react-bootstrap';

interface FormFieldProps {
  label: string;
  name: string;
  type: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  required?: boolean;
  placeholder?: string;
  error?: string;
  as?: 'input' | 'textarea';
  rows?: number;
}

const FormField: React.FC<FormFieldProps> = ({
  label,
  name,
  type,
  value,
  onChange,
  required = false,
  placeholder,
  error,
  as = 'input',
  rows
}) => {
  const formControlProps = {
    as,
    type,
    name,
    value,
    onChange,
    required,
    placeholder,
    isInvalid: !!error
  };

  if (as === 'textarea' && rows) {
    (formControlProps as any).rows = rows;
  }

  return (
    <Form.Group className="mb-3">
      <Form.Label>{label}</Form.Label>
      <Form.Control {...formControlProps} />
      {error && (
        <Form.Control.Feedback type="invalid">
          {error}
        </Form.Control.Feedback>
      )}
    </Form.Group>
  );
};

export default FormField; 