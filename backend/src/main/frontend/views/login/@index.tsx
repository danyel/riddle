import {UserService} from "Frontend/generated/endpoints";
import {LoginOverlay, LoginOverlayElement} from '@vaadin/react-components/LoginOverlay.js';
import {useRef, useState} from 'react';
import {Navigate} from 'react-router';
import {configureAuth} from '@vaadin/hilla-react-auth';
import {login as hillaLogin} from '@vaadin/hilla-frontend';

export default function Login() {
    const {useAuth, AuthProvider} = configureAuth(UserService.getAuthenticatedUser, {});
    const [error, setError] = useState(false);
    const loginOverlayRef = useRef<LoginOverlayElement>(null);

    // If already logged in, redirect to home
    if (useAuth().state.user) {
        return <Navigate to="/secured/"/>;
    }

    return (
        <LoginOverlay
            ref={loginOverlayRef}
            opened
            error={error}
            noForgotPassword // Disable if you don't have a reset flow
            title="Riddler App"
            description="Please log in to continue"
            onLogin={async (e) => {
                setError(false);
                const {username, password} = e.detail;
                console.log('User', username, password);
                const result = await hillaLogin(username, password);

                if (result.error) {
                    setError(true);
                }
            }}
        />
    );
}