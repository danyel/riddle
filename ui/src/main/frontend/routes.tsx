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
