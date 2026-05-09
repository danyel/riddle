import {LoginOverlay, LoginOverlayElement} from '@vaadin/react-components/LoginOverlay.js';
import {useRef, useState} from 'react';
import {useAuth} from 'Frontend/auth';
import {useNavigate} from "react-router"; // Import from your new auth.ts

export default function LoginView() {
    const {login} = useAuth(); // 'authenticate' updates the AuthProvider state
    const [error, setError] = useState(false);
    const navigate = useNavigate();
    const loginOverlayRef = useRef<LoginOverlayElement>(null);

    // // If already logged in, redirect to home
    // if (state.user) {
    //     return <Navigate to="/" replace/>;
    // }

    return (
        <LoginOverlay
            ref={loginOverlayRef}
            opened
            error={error}
            noForgotPassword
            title="Riddler App"
            description="Please log in to continue"
            onLogin={async (e) => {
                setError(false);
                const {username, password} = e.detail;

                // This calls your Java UserEndpoint.authenticate AND
                // tells Hilla to manage the resulting UserInfo object.
                const result = await login(username, password);

                if (result.error) {
                    setError(true);
                    if (loginOverlayRef.current) {
                        loginOverlayRef.current.disabled = false;
                    }
                } else {
                    navigate('/')
                }
            }}
        />
    );
}
