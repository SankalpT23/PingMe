import { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem('pingme_token'));
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem('pingme_user');
    return saved ? JSON.parse(saved) : null;
  });

  const login = (jwtToken, userData) => {
    localStorage.setItem('pingme_token', jwtToken);
    localStorage.setItem('pingme_user', JSON.stringify(userData));
    setToken(jwtToken);
    setUser(userData);
  };

  const logout = () => {
    localStorage.removeItem('pingme_token');
    localStorage.removeItem('pingme_user');
    setToken(null);
    setUser(null);
  };

  const isAuthenticated = !!token;

  return (
    <AuthContext.Provider value={{ token, user, login, logout, isAuthenticated }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}
