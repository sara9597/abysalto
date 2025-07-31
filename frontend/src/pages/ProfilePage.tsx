import React from 'react';
import { Card, Row, Col, Button } from 'react-bootstrap';
import { useProfile } from './hooks/useProfile';
import { LoadingComponent, LoginWarning, PageHeader } from '../components';

const ProfilePage: React.FC = () => {
  const {
    loading,
    currentUser,
    handleLogout
  } = useProfile();

  if (loading) {
    return <LoadingComponent />;
  }

  if (!currentUser) {
    return <LoginWarning />;
  }

  return (
    <div>
      <PageHeader title="User Profile" />
      
      <Card>
        <Card.Body>
          <Row>
            <Col md={6}>
              <h5>Personal Information</h5>
              <p><strong>Name:</strong> {currentUser.firstName} {currentUser.lastName}</p>
              <p><strong>Username:</strong> {currentUser.username}</p>
              <p><strong>Email:</strong> {currentUser.email}</p>
            </Col>
            <Col md={6}>
              <h5>Contact Information</h5>
              <p><strong>Phone:</strong> {currentUser.phone || 'Not provided'}</p>
              <p><strong>Address:</strong> {currentUser.address || 'Not provided'}</p>
            </Col>
          </Row>
          
          <div className="mt-4">
            <Button variant="primary" className="me-2">
              Edit Profile
            </Button>
            <Button variant="danger" onClick={handleLogout}>
              Logout
            </Button>
          </div>
        </Card.Body>
      </Card>
    </div>
  );
};

export default ProfilePage; 