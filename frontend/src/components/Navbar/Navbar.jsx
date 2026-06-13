import { useAuth } from '../../context/AuthContext.jsx';
import { useNavigate } from 'react-router-dom';
import './Navbar.css';

function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar glass-strong" id="main-navbar">
      <div className="navbar-brand" id="navbar-brand">
        <span className="navbar-logo">⚡</span>
        <span className="navbar-title">PingMe</span>
        <span className="navbar-badge">Dashboard</span>
      </div>
      <div className="navbar-right">
        {user && (
          <div className="navbar-user" id="navbar-user-info">
            <div className="user-avatar">
              {user.email ? user.email[0].toUpperCase() : 'U'}
            </div>
            <span className="user-email">{user.email}</span>
          </div>
        )}
        <button
          className="btn-logout"
          id="logout-button"
          onClick={handleLogout}
        >
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
            <polyline points="16 17 21 12 16 7" />
            <line x1="21" y1="12" x2="9" y2="12" />
          </svg>
          Logout
        </button>
      </div>
    </nav>
  );
}

export default Navbar;
