import React from 'react';
import { Link } from 'react-router-dom';
import { useLogin } from './hooks/useLogin';
import { FormCard, FormField } from '../components';

const LoginPage: React.FC = () => {
  const {
    error,
    handleSubmit,
    formData,
    handleChange,
    loading,
  } = useLogin();

  return (
    <FormCard
      title="Login"
      onSubmit={handleSubmit}
      loading={loading}
      error={error}
      submitText="Login"
      loadingText="Logging in..."
    >
      <FormField
        label="Username"
        name="username"
        type="text"
        value={formData.username}
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

      <div className="text-center mt-3">
        <p>Don't have an account? <Link to="/register">Register here</Link></p>
      </div>
    </FormCard>
  );
};

export default LoginPage; 