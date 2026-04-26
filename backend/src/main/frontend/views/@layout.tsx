// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';

import {AppLayout} from '@vaadin/react-components/AppLayout.js';
import {Outlet} from 'react-router';
import Navigation from "Frontend/components/navigation/Navigation";
import {Icon, SideNav, SideNavItem, VerticalLayout} from "@vaadin/react-components";
import {useState} from "react";
import SideBar from "Frontend/components/navigation/side-bar.component";

export default function MainLayout() {
    const [isOpened, setIsOpened] = useState(true);
    return (
        <AppLayout  primarySection="navbar"
                    drawerOpened={isOpened}
                    onDrawerOpenedChanged={({ detail }) => setIsOpened(detail.value)}
        >
            {/* 1. Add a header if you want the navigation to show up properly */}
            <Navigation/>
            <section slot={'drawer'} className={styles.side_bar}>
               <SideBar/>
            </section>
            <main className={`${styles.content_container} ${isOpened ? 'drawer-is-open' : 'drawer-is-closed'}`}>
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

