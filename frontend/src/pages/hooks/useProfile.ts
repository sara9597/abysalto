import {User} from "../../types/api";
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';

export const useProfile = () => {
    const navigate = useNavigate();
    const [currentUser, setCurrentUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const user = localStorage.getItem('currentUser');
        if (user) {
            setCurrentUser(JSON.parse(user));
        }
        setLoading(false);
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('currentUser');
        setCurrentUser(null);
        navigate('/login');
    };

    return {
        loading,
        currentUser,
        handleLogout
    } as const;
}