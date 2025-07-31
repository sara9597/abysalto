import {UserRegistrationRequest} from "../../types/api";
import {usersApi} from "../../services/api";
import { useNavigate } from 'react-router-dom';
import {useState} from 'react'
export const useRegister = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState<UserRegistrationRequest>({
        username: '',
        email: '',
        password: '',
        firstName: '',
        lastName: '',
        phone: '',
        address: ''
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            const user = await usersApi.register(formData);
            localStorage.setItem('currentUser', JSON.stringify(user));
            navigate('/products');
        } catch (err) {
            setError('Registration failed. Username or email might already be taken.');
            console.error('Registration error:', err);
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    return {
        error,
        handleSubmit,
        formData,
        handleChange,
        loading
    } as const;
};