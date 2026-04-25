// @ts-ignore
import styles from './layout.module.css';

import {AppLayout} from '@vaadin/react-components/AppLayout.js';
import {DrawerToggle} from '@vaadin/react-components/DrawerToggle.js';
import {Outlet} from 'react-router';
import Navigation from "Frontend/components/navigation/Navigation";
import {VerticalLayout} from "@vaadin/react-components";

export default function MainLayout() {
    return (
        <AppLayout>
            {/* 1. Add a header if you want the navigation to show up properly */}
            <div slot="navbar" className={styles.navbar_container}>
                <DrawerToggle/>
                <h2 style={{margin: '0 var(--lumo-space-m)', fontSize: 'var(--lumo-font-size-l)'}}>
                    My App
                </h2>
                <Navigation/>
            </div>

            {/* 2. The Outlet is where Index renders */}
            <main className={styles.content_container}>
                <VerticalLayout className={styles.content}>
                    <Outlet/>
                </VerticalLayout>
                <div className={styles.footer_container}>
                    <footer>
                        footer
                    </footer>
                </div>
            </main>

        </AppLayout>
    );
}

