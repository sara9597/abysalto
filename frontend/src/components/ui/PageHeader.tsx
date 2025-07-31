import React from 'react';

interface PageHeaderProps {
  title: string;
  subtitle?: string;
}

const PageHeader: React.FC<PageHeaderProps> = ({ title, subtitle }) => {
  return (
    <div className="mb-4">
      <h2>{title}</h2>
      {subtitle && <p className="text-muted">{subtitle}</p>}
    </div>
  );
};

export default PageHeader; 