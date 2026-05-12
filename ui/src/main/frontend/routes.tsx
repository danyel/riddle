// frontend/routes.tsx
import {protectRoutes} from '@vaadin/hilla-react-auth';
import MainLayout from "Frontend/views/secured/@layout";
import QuestionsView from "Frontend/views/secured/questions/@index";
import HomeView from "Frontend/views/secured/@index";
import LoginView from "Frontend/views/login/@index";
import LoginLayout from "Frontend/views/login/@layout";
import IconsView from "Frontend/views/secured/icons/@index";
import {createHashRouter, isRouteErrorResponse, useRouteError} from "react-router-dom";
import QuestionDetailView from "Frontend/views/secured/question/{id}";
import ParticipantLayout from "Frontend/views/participant/@layout";
import ParticipantQuestionsView from "Frontend/views/participant/question/view-question-page";
import ParticipantView from "Frontend/views/participant/@index";
import ParticipantsView from "Frontend/views/secured/participants/@index";
import {ParticipantDetailView} from "Frontend/views/secured/participants/participant-detail-view";


function RootErrorBoundary() {
    const error = useRouteError();
    console.error(error);

    if (isRouteErrorResponse(error) && error.status === 404) {
        return (
            <div style={{padding: '2rem', textAlign: 'center'}}>
                <h2>Oops! Page Not Found (404)</h2>
                <p>The page you are looking for does not exist or has moved.</p>
                <a href="/">Go back to Home</a>
            </div>
        );
    }

    return (
        <div style={{padding: '2rem', textAlign: 'center'}}>
            <h2>An Unexpected Error Occurred {isRouteErrorResponse(error)}</h2>
            <p>Something went wrong. Please reload the app.</p>
        </div>
    );
}

export const routes = protectRoutes([
    {
        element: <MainLayout/>,
        errorElement: <RootErrorBoundary/>,
        children: [
            {
                path: '/',
                element: <HomeView/>,
                handle: {rolesAllowed: ['ROLE_ADMIN', 'ROLE_USER']}
            },
            {
                path: '/participants',
                element: <ParticipantsView/>,
                handle: {rolesAllowed: ['ROLE_ADMIN', 'ROLE_USER']} // Client-side check
            },
            {
                path: '/participants/:id',
                element: <ParticipantDetailView/>,
                handle: {rolesAllowed: ['ROLE_ADMIN', 'ROLE_USER']} // Client-side check
            },
            {
                path: '/questions',
                element: <QuestionsView/>,
                handle: {rolesAllowed: ['ROLE_ADMIN', 'ROLE_USER']} // Client-side check
            },
            {
                path: '/question/:id',
                element: <QuestionDetailView/>,
                handle: {rolesAllowed: ['ROLE_ADMIN', 'ROLE_USER']} // Client-side check
            },
            {
                path: '/icons',
                element: <IconsView/>,
                handle: {rolesAllowed: ['ROLE_ADMIN']} // Client-side check
            },
        ],
    },
    {
        element: <ParticipantLayout/>,
        errorElement: <RootErrorBoundary/>,
        children: [
            {
                path: '/participant',
                element: <ParticipantView/>
            },
            {
                path: '/participant/question/:id',
                element: <ParticipantQuestionsView/>
            }
        ]
    },
    {
        element: <LoginLayout/>,
        children: [
            {
                path: '/login',
                element: <LoginView/>
            }
        ]
    },
    {
        path: '*',
        element: <RootErrorBoundary/>
    }
]);

export const router = createHashRouter(routes);
export default router;
