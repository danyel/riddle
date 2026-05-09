// frontend/routes.tsx
import {protectRoutes} from '@vaadin/hilla-react-auth';
import MainLayout from "Frontend/views/secured/@layout";
import QuestionsView from "Frontend/views/secured/questions/@index";
import HomeView from "Frontend/views/secured/@index";
import LoginView from "Frontend/views/login/@index";
import LoginLayout from "Frontend/views/login/@layout";
import IconsView from "Frontend/views/secured/icons/@index";
import {createBrowserRouter} from "react-router-dom";
import QuestionDetailView from "Frontend/views/secured/question/{id}";
import ParticipantLayout from "Frontend/views/participant/@layout";
import ViewQuestionPage from "Frontend/views/participant/question/view-question-page";
import ParticipantPage from "Frontend/views/participant/@index";

export const routes = protectRoutes([
    {
        element: <MainLayout/>,
        children: [
            {
                path: '/',
                element: <HomeView/>,
                handle: {rolesAllowed: ['ROLE_ADMIN', 'ROLE_USER']}
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
        children: [
            {
                path: '/participant',
                element: <ParticipantPage/>
            },
            {
                path: '/participant/question/:id',
                element: <ViewQuestionPage/>
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
]);

export const router = createBrowserRouter(routes);
export default router;
