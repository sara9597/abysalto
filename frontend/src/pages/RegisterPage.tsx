import React from 'react';
import { Row, Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useRegister } from './hooks/useRegister';
import { FormCard, FormField } from '../components';

const RegisterPage: React.FC = () => {
  const {
    error,
    handleSubmit,
    formData,
    handleChange,
    loading
  } = useRegister();

  return (
    <FormCard
      title="Register"
      onSubmit={handleSubmit}
      loading={loading}
      error={error}
      submitText="Register"
      loadingText="Registering..."
      style={{ width: '500px' }}
    >
      <Row>
        <Col md={6}>
          <FormField
            label="First Name"
            name="firstName"
            type="text"
            value={formData.firstName}
            onChange={handleChange}
            required
          />
        </Col>
        <Col md={6}>
          <FormField
            label="Last Name"
            name="lastName"
            type="text"
            value={formData.lastName}
            onChange={handleChange}
            required
          />
        </Col>
      </Row>

      <FormField
        label="Username"
        name="username"
        type="text"
        value={formData.username}
        onChange={handleChange}
        required
      />

      <FormField
        label="Email"
        name="email"
        type="email"
        value={formData.email}
        onChange={handleChange}
        required
      />

      <FormField
        label="Password"
        name="password"
        type="password"
        value={formData.password}
        onChange={handleChange}
        required
      />

      <FormField
        label="Phone"
        name="phone"
        type="tel"
        value={formData.phone}
        onChange={handleChange}
      />

      <FormField
        label="Address"
        name="address"
        type="text"
        value={formData.address}
        onChange={handleChange}
        as="textarea"
        rows={3}
      />

      <div className="text-center mt-3">
        <p>Already have an account? <Link to="/login">Login here</Link></p>
      </div>
    </FormCard>
  );
};

export default RegisterPage; 